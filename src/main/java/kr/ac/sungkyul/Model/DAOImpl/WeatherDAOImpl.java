package kr.ac.sungkyul.Model.DAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.swing.event.ListSelectionEvent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.Model.DAO.WeatherDAO;
import kr.ac.sungkyul.Model.DTO.DustDTO;
import kr.ac.sungkyul.Model.DTO.WeatherDTO;
import kr.ac.sungkyul.Utils.DateUtils;
import kr.ac.sungkyul.Utils.FileUtils;
import kr.ac.sungkyul.Utils.JSONUtils;


@Repository
public class WeatherDAOImpl implements WeatherDAO{
	@Autowired
	private MongoTemplate mongoTemplate;
	private String host = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?";
	private String key = "WXNSnP9oNtjXfyMMiIowrMSq94RdkmTD9KvPUjoHueX4qOdqOPKawkseFA0xw5eOuYIwsEyW6EAIRG9kAoMNzA%3D%3D";
	private String fixedParam = "&numOfRows=300&_type=json";
	private List<Integer> loopVal = Arrays.asList(12, 9, 11, 10, 11, 9, 11, 9);
	private List<String> baseTimeList = Arrays.asList("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300");
//	private List<String> nxList = Arrays.asList("60","98","89","55","58","67","60","73","69","68","63","51","89","91","52");
//	private List<String> nyList = Arrays.asList("127","76","90","124","74","100","120","134","107","100","89","67","91","77","38");
	private List<String> nXnYList;
	private String resultMsg;
	private WeatherDTO preDTO = null;

	WeatherDAOImpl() {
		try {
			nXnYList = new ArrayList<String>(FileUtils.readAndMakeSetFromExcel());
		} catch (IOException e) {
			System.out.println("DAO List생성 error");
			e.printStackTrace();
		}
	}
	@Override
	public void parsingAndSaving() {
		try {
			// 오늘 날짜 겟또
//			SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
//			SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
//			String base_date = ymdFormat.format(date); // yyyyMMdd
//			String nowHour = hourFormat.format(date); // HH
			DateUtils.calculate();
			String base_date = DateUtils.getBaseDate();
//			String nowHour;
			String base_time = DateUtils.getBaseTime();
//			for (int k = 0; k < 8; k++) {
				for (int m = 0; m < nXnYList.size(); m++) {
					System.out.println("m size = " + nXnYList.size() + ", m =" + m);
					int basicIndex = (DateUtils.getHour() / 3) - 1;
//					int loopIndex = (basicIndex + k) % 8;
//					String base_time = baseTimeList.get(loopIndex);
					System.out.println("측정시간 = " + base_time + ", 측정날짜 = " + base_date);
					System.out.println("nXnYList = " + nXnYList.get(m));
					String[] location = nXnYList.get(m).split("@"); // nx = location[0], ny location[1]
					int nx = (int) Double.parseDouble(location[0]);
					int ny = (int) Double.parseDouble(location[1]);
					System.out.println("측정위치 = nx : " + nx + ", ny : " + ny);
					JSONArray items = getWeatherAPI(base_date, base_time, nx, ny);

					if ("OK".equals(resultMsg)) {
//						analysisJsonArray(items, loopIndex, base_date);
						analysisJsonArray(items, base_date);
						preDTO = null;
					}
					resultMsg = null;
				}
//			}

			////////////////////////////////////////
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public JSONArray getWeatherAPI(String base_date, String base_time, int nx, int ny)
			throws UnsupportedEncodingException, IOException {
		URL url = new URL(host + "ServiceKey=" + key + "&base_date=" + base_date + "&base_time=" + base_time + "&nx="
				+ nx + "&ny=" + ny + "&pageNo=1" + fixedParam);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		JSONTokener jt = new JSONTokener(br);
		JSONObject jo = new JSONObject(jt);
		resultMsg = (String) jo.getJSONObject("response").getJSONObject("header").get("resultMsg");
		JSONArray items = jo.getJSONObject("response").getJSONObject("body").getJSONObject("items")
				.getJSONArray("item");
		return items;
	}
	private void analysisJsonArray(JSONArray items, String base_date) {
		WeatherDTO wDTO = new WeatherDTO();
		Hashtable<String, String> map = new Hashtable<String, String>();
		for(int j = 0; j<loopVal.size(); j++) {
		int cycle = loopVal.get(j);
		System.out.println(" cycle = "+cycle);
		for (int i = 0; i < cycle; i++) {
			JSONObject tmpJo = (JSONObject) items.get(i);
			System.out.println(tmpJo);
			if (preDTO == null) {
//				System.out.println("fcstDate" + JSONUtils.optString(tmpJo, "fcstDate", "N/A"));
//				System.out.println("fcstTime" + JSONUtils.optString(tmpJo, "fcstTime", "N/A"));
//				System.out.println("nx" + JSONUtils.optString(tmpJo, "nx", "N/A"));
//				System.out.println("ny" + JSONUtils.optString(tmpJo, "ny", "N/A"));
				if (i == 0) {
					wDTO.setFcstDate(JSONUtils.optString(tmpJo, "fcstDate", "N/A"));
					wDTO.setFcstTime(JSONUtils.optString(tmpJo, "fcstTime", "N/A"));
					wDTO.setBaseTime(base_date);
					wDTO.setNx(JSONUtils.optString(tmpJo, "nx", "N/A"));
					wDTO.setNy(JSONUtils.optString(tmpJo, "ny", "N/A"));
				}
				map.put(JSONUtils.optString(tmpJo, "category", "N/A"), JSONUtils.optString(tmpJo, "fcstValue", "N/A"));
			} else {

			}
		}
	}
		System.out.println("category" + map.toString());
		wDTO.setCategory(map);
		insertDataIntoDB(wDTO);
	}
//	private void analysisJsonArray(JSONArray items, int loopIndex, String base_date) {
//		WeatherDTO wDTO = new WeatherDTO();
//		Hashtable<String, String> map = new Hashtable<String, String>();
//		int cycle = loopVal.get(loopIndex);
//		for (int i = 0; i < cycle; i++) {
//			JSONObject tmpJo = (JSONObject) items.get(i);
//			System.out.println(tmpJo);
//			if (preDTO == null) {
//				System.out.println("fcstDate" + JSONUtils.optString(tmpJo, "fcstDate", "N/A"));
//				System.out.println("fcstTime" + JSONUtils.optString(tmpJo, "fcstTime", "N/A"));
//				System.out.println("nx" + JSONUtils.optString(tmpJo, "nx", "N/A"));
//				System.out.println("ny" + JSONUtils.optString(tmpJo, "ny", "N/A"));
//				if (i == 0) {
//					wDTO.setFcstDate(JSONUtils.optString(tmpJo, "fcstDate", "N/A"));
//					wDTO.setFcstTime(JSONUtils.optString(tmpJo, "fcstTime", "N/A"));
//					wDTO.setBaseTime(base_date);
//					wDTO.setNx(JSONUtils.optString(tmpJo, "nx", "N/A"));
//					wDTO.setNy(JSONUtils.optString(tmpJo, "ny", "N/A"));
//				}
//				map.put(JSONUtils.optString(tmpJo, "category", "N/A"), JSONUtils.optString(tmpJo, "fcstValue", "N/A"));
//			} else {
//
//			}
//		}
//		System.out.println("category" + map.toString());
//		wDTO.setCategory(map);
//		insertDataIntoDB(wDTO);
//	}
	
	@Override
	public void insertDataIntoDB(WeatherDTO weatherDTO) {
		mongoTemplate.insert(weatherDTO, "weather");
		System.out.println("weather to save to MongoDB");
	}
	
	@Override
	public WeatherDTO findWeather(String nx, String ny) {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DAY_OF_MONTH);
		date = date - 1;
		System.out.println("date : " + date);
		cal.set(Calendar.DAY_OF_MONTH, date);
		SimpleDateFormat d_format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat d_format_minute = new SimpleDateFormat("HH");
		
		String cal_Criteria = d_format.format(cal.getTime());
		System.out.println("cal_Criteria : " +cal_Criteria);
		String cal_Cri_hh = d_format_minute.format(cal.getTime());
		System.out.println("cal_Cri_hh1 : " + cal_Cri_hh);
		int c = Integer.parseInt(cal_Cri_hh);
		if(c>=0&&c<=2) {
			cal_Cri_hh = "0000";
		} else if(c>=3&&c<=5) {
			cal_Cri_hh = "0300";
		} else if(c>=6&&c<=8) {
			cal_Cri_hh = "0600";
		} else if(c>=9&&c<=11){
			cal_Cri_hh = "0900";
		} else if(c>=12&&c<=14){
			cal_Cri_hh = "1200";
		} else if(c>=15&&c<=17){
			cal_Cri_hh = "1500";
		} else if(c>=18&&c<=20){
			cal_Cri_hh = "1800";
		} else if(c>=21&&c<=23){
			cal_Cri_hh = "2100";
		}
		System.out.println("cal_Cri_hh2 : "+cal_Cri_hh);
		WeatherDTO dto = null;
		Criteria criteria = new Criteria("nx");
		/*criteria.is("55").and("ny").is("127").and("baseTime").is("20181211").and("fcstDate")
		.is("20181211").and("fcstTime").is("0600");*/
		criteria.is("60").and("ny").is("127").and("baseTime").is(cal_Criteria).and("fcstDate")
		.is(cal_Criteria).and("fcstTime").is(cal_Cri_hh);
		Query query = new Query(criteria);
		dto = mongoTemplate.findOne(query, WeatherDTO.class, "weather");
		System.out.println(nx + ":" + ny);
		System.out.println(dto.getFcstDate());
		System.out.println(dto.getFcstTime());
		System.out.println(dto.getFcstTime());
		System.out.println(dto.getCategory().get("POP"));
		return dto;
	}
	
}
