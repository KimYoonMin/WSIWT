package kr.ac.sungkyul.Model.DAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

import kr.ac.sungkyul.Model.DAO.DustDAO;
import kr.ac.sungkyul.Model.DTO.DustDTO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;
import kr.ac.sungkyul.Model.DTO.WeatherDTO;
import kr.ac.sungkyul.Utils.JSONUtils;

@Repository
public class DustDAOImpl implements DustDAO{
	@Autowired
	private MongoTemplate mongoTemplate;
	private String city[][] = { { "서울", "25" }, { "부산", "16" }, { "대구", "8" }, { "인천", "9" }, { "광주", "5" },
			{ "대전", "10" }, { "경기", "31" }, { "강원", "12" }, { "충북", "7" }, { "충남", "14" }, { "전북", "13" },
			{ "전남", "10" }, { "경북", "8" }, { "경남", "9" }, { "제주", "2" } };

	private String host = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst";
	private String key = "IHiUfhLMUy0T0Ea%2BF8LvzGW9dogtO%2FA%2BwU%2BRT2h%2BPdPlL22XwN0lL9JNilVUEW1qcIX8bfYFw4Wia4E7KfBO%2Fw%3D%3D";
	private String fixedParam = "&searchCondition=DAILY&pageNo=1&_returnType=json";

	public void parsingAndSaving() {
		try {
			for (int i = 0; i < city.length; i++) {
				JSONArray jArray = getDustAPI(i);
				analysisJsonArray(jArray, city[i][0]);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	@Override
	public JSONArray getDustAPI(int i) throws IOException {
		String sidoName = URLEncoder.encode(city[i][0], "UTF-8");
		String rows = city[i][1];
		System.out.println(city[i][0] + " 지역  parsing 중  ");
		URL url = new URL(host + "?sidoName=" + sidoName + "&numOfRows=" + rows + "&ServiceKey=" + key + fixedParam);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		JSONTokener jt = new JSONTokener(br);
		JSONObject jo = new JSONObject(jt);
		JSONArray jArray = (JSONArray) jo.get("list");
		br.close();
		return jArray;
	}
	
	@Override
	public void analysisJsonArray(JSONArray jArray, String sidoName) {
		for (int j = 0; j < jArray.length(); j++) {
			JSONObject tmpJo = (JSONObject) jArray.getJSONObject(j);
			DustDTO dto = new DustDTO();
			System.out.println(tmpJo);
			System.out.println("cityName = " + JSONUtils.optString(tmpJo, "cityName", "no value"));
			System.out.println("dataTime = " + JSONUtils.optString(tmpJo, "dataTime"));
			System.out.println("미세먼지 수치 = " + JSONUtils.optString(tmpJo, "pm10Value", "-1"));
			System.out.println("초 미세먼지 수치 = " + JSONUtils.optString(tmpJo, "pm25Value", "-1"));
			dto.setSidoName(sidoName);
			dto.setCityName(JSONUtils.optString(tmpJo, "cityName", "no value"));
			dto.setDataTime(JSONUtils.optString(tmpJo, "dataTime"));
			dto.setPm10Value(JSONUtils.optString(tmpJo, "pm10Value", "-1"));
			dto.setPm25Value(JSONUtils.optString(tmpJo, "pm25Value", "-1"));
			dto.setPm10Grade(setPm10GradeVal(JSONUtils.optString(tmpJo, "pm10Value", "-1")));
			dto.setPm25Grade(setPm25GradeVal(JSONUtils.optString(tmpJo, "pm25Value", "-1")));
			System.out.println("미세먼지 등급 " + dto.getPm10Grade());
			System.out.println("초 미세먼지 등급 = " + dto.getPm25Grade());
			insertDataIntoDB(dto);
		}
	}
	@Override
	public void insertDataIntoDB(DustDTO dustDTO) {
		mongoTemplate.insert(dustDTO, "dust");
		System.out.println("dust to save to MongoDB");
	}
	@Override
	public int setPm10GradeVal(String pm10Value) {
		if (pm10Value != null) {
			int intVal = Integer.parseInt(pm10Value);
			if (intVal < 0) {
				return -1;
			} else if (0 <= intVal && intVal <= 30) {
				return 1;
			} else if (31 <= intVal && intVal <= 80) {
				return 2;
			} else if (81 <= intVal && intVal <= 150) {
				return 3;
			} else if (151 <= intVal) {
				return 4;
			}
		}
		return 0;
	}
	@Override
	public int setPm25GradeVal(String pm25Value) {
		if (pm25Value != null) {
			int intVal = Integer.parseInt(pm25Value);
			if (intVal < 0) {
				return -1;
			} else if (0 <= intVal && intVal <= 15) {
				return 1;
			} else if (16 <= intVal && intVal <= 50) {
				return 2;
			} else if (51 <= intVal && intVal <= 100) {
				return 3;
			} else if (101 <= intVal) {
				return 4;
			}
		}
		return 0;
	}
	
	public DustDTO findDust(LocationDTO dto) {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);
		hour = hour-1;
		System.out.println("hour"+hour);
		
		cal.set(Calendar.HOUR, hour);
		SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH");
		
		String cal_Criteria_imsi = d_format.format(cal.getTime());
		String cal_Criteria = cal_Criteria_imsi+":00";
		System.out.println("cal_Criteria : " + cal_Criteria);
		
		DustDTO ddto = null;
		String getCity = dto.getCity().replace("도", "");
		getCity = getCity.replace("특별시", "");
		getCity = getCity.replace("광역시", "");
		getCity = getCity.replace("특별자치시", "");		
		System.out.println("getCity : " + getCity);
		Query query = new Query();
		query.addCriteria(Criteria.where("sidoName").is(getCity).and("dataTime").is(cal_Criteria));		
		ddto = mongoTemplate.findOne(query, DustDTO.class, "dust");
		System.out.println("ddto : " + ddto);
		System.out.println("getPm10Grade : "+ddto.getPm10Grade());
		System.out.println("getPm10Value : "+ddto.getPm10Value());
		System.out.println("getPm25Grade : "+ddto.getPm25Grade());
		System.out.println("getPm25Value : "+ddto.getPm25Value());
		
		return ddto;
	}

}
