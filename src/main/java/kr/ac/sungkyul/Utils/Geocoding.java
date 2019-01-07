package kr.ac.sungkyul.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 주소를 위도 경도로 변환하는 코드 GoogleAPI 사용 2018-11-22 박희해
 **/
public class Geocoding {
	String location;
	Double[] latlng = new Double[2];

	public Geocoding(String location) throws Exception {
		this.location = location;
		this.latlng = getRegionLatLng(getJSONData(getApiAddress()));
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private String getApiAddress() {
		try {
			String apiURL = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyD5DUJ7QLDELHMpEXsSi5qbRVk-FpbkUpo&address="
					+ URLEncoder.encode(location, "UTF-8") + "&language=ko";
			return apiURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getJSONData(String apiURL) throws Exception {
		String jsonString = new String();
		String buf;
		URL url = new URL(apiURL);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while ((buf = br.readLine()) != null) {
			jsonString += buf;
		}
		return jsonString;
	}

	private Double[] getRegionLatLng(String jsonString) {
//		JSONObject jObj = (JSONObject) JSONValue.parse(jsonString);
		JSONTokener jt = new JSONTokener(jsonString);
		JSONObject jObj = new JSONObject(jt);
		JSONArray jArray = (JSONArray) jObj.get("results");
		jObj = (JSONObject) jArray.get(0);
		JSONObject jObjGeo = (JSONObject) jObj.get("geometry");
		JSONObject jObjLoc = (JSONObject) jObjGeo.get("location");
		latlng[0] = (Double) jObjLoc.get("lat");
		latlng[1] = (Double) jObjLoc.get("lng");
		return latlng;
	}

	public Double[] getLatlng() {
		return latlng;
	}
}
