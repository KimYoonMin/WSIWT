package kr.ac.sungkyul.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class JSONUtils {
	public static String optString(JSONObject jsonObj,String mapKey, String defaultVal) {
		String str = jsonObj.get(mapKey).toString().trim();
		if(str == null || str.equals("")) {
			return defaultVal;
		}
		return str;
	}
	public static String optString(JSONObject jsonObj,String mapKey) {
		String str = jsonObj.get(mapKey).toString().trim();
		if(str == null || str.equals("")) {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
			String time = sdf.format(date);
			return time;
		}
		return str;
	}
}
