package kr.ac.sungkyul.Model.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.Model.DTO.WeatherDTO;
import kr.ac.sungkyul.Utils.JSONUtils;

@Repository("weatherDAO")
public class WeatherDAO {
	@Autowired
	private MongoTemplate mongoTemplate;
	private String host = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?";
	private String key = "IHiUfhLMUy0T0Ea%2BF8LvzGW9dogtO%2FA%2BwU%2BRT2h%2BPdPlL22XwN0lL9JNilVUEW1qcIX8bfYFw4Wia4E7KfBO%2Fw%3D%3D";
	private String fixedParam = "&numOfRows=300&_type=json";
	private List<Integer> loopVal = Arrays.asList(12, 9, 11, 10, 11, 9, 11, 9);
	private List<String> baseTimeList = Arrays.asList("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300");
	private List<String> nxList = Arrays.asList("60", "98", "89", "55", "58", "67", "60", "73", "69", "68", "63", "51",
			"89", "91", "52");
	private List<String> nyList = Arrays.asList("127", "76", "90", "124", "74", "100", "120", "134", "107", "100", "89",
			"67", "91", "77", "38");
	private String resultMsg;
	private WeatherDTO preDTO = null;

	public void parsingAndSaving(Date date) {
		try {
			// 오늘 날짜 겟또
			SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
			String base_date = ymdFormat.format(date); // yyyyMMdd
			String nowHour = hourFormat.format(date); // HH
			// 위치는 geoAPI로
//	         int nx = 55;
//	         int ny = 127;
			///////////////////////////

			for (int k = 0; k < 8; k++) {
				for (int m = 0; m < nxList.size(); m++) {
					int basicIndex = Integer.parseInt(nowHour) / 3;
					int loopIndex = (basicIndex + k) % 8;
					String base_time = baseTimeList.get(loopIndex);
					System.out.println("측정시간 =" + base_time);
					System.out.println("측정위치" + nxList.get(m) + "///" + nyList.get(m));
					JSONArray items = getWeatherAPI(base_date, base_time, nxList.get(m), nyList.get(m));
					if ("OK".equals(resultMsg)) {
						analysisJsonArray(items, loopIndex, base_date);
						preDTO = null;
					}
					resultMsg = null;
				}
			}

			////////////////////////////////////////
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private JSONArray getWeatherAPI(String base_date, String base_time, String nx, String ny)
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

	private void analysisJsonArray(JSONArray items, int loopIndex, String base_date) {
		WeatherDTO wDTO = new WeatherDTO();
		Hashtable<String, String> map = new Hashtable<String, String>();
		int cycle = loopVal.get(loopIndex);
		System.out.println("cycle=" + cycle);
		for (int i = 0; i < cycle; i++) {
			JSONObject tmpJo = (JSONObject) items.get(i);
			System.out.println(tmpJo);
			System.out.println("fcstDate" + JSONUtils.optString(tmpJo, "fcstDate", "no value"));
			System.out.println("fcstTime" + JSONUtils.optString(tmpJo, "fcstTime", "no value"));
			System.out.println("nx" + JSONUtils.optString(tmpJo, "nx", "no value"));
			System.out.println("ny" + JSONUtils.optString(tmpJo, "ny", "no value"));
			if (i == 0) {
				wDTO.setFcstDate(JSONUtils.optString(tmpJo, "fcstDate", "no value"));
				wDTO.setFcstTime(JSONUtils.optString(tmpJo, "fcstTime", "no value"));
				wDTO.setBaseTime(base_date);
				wDTO.setNx(JSONUtils.optString(tmpJo, "nx", "no value"));
				wDTO.setNy(JSONUtils.optString(tmpJo, "ny", "no value"));
			}
			map.put(JSONUtils.optString(tmpJo, "category", "no value"),
					JSONUtils.optString(tmpJo, "fcstValue", "no value"));

		}
		System.out.println("category" + map.toString());
		wDTO.setCategory(map);
		insertDataIntoDB(wDTO);
	}

	private void insertDataIntoDB(WeatherDTO weatherDTO) {
		mongoTemplate.insert(weatherDTO, "weather");
		System.out.println("weather to save to MongoDB");
	}

	public WeatherDTO findWeather(String nx, String ny) {
		WeatherDTO dto = null;
		Criteria criteria = new Criteria("nx");
		criteria.is("60").and("ny").is("127");
		Query query = new Query(criteria);
		dto = mongoTemplate.findOne(query, WeatherDTO.class, "weather");
		System.out.println(nx + ":" + ny);
		System.out.println(dto.getFcstDate());
		System.out.println(dto.getFcstTime());
		System.out.println(dto.getCategory().get("pop"));
		return dto;
	}
}
