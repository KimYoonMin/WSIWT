package kr.ac.sungkyul.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	private static int mHour;
	private static String base_time;
	private static String base_date;

	public static String getBaseTime() {
		return base_time;
	}

	public static String getBaseDate() {
		return base_date;
	}
	public static int getHour() {
		return mHour;
	}

	public static void calculate() {
		Calendar calendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int nHour = calendar.get(Calendar.HOUR_OF_DAY);
		int nMinute = calendar.get(Calendar.MINUTE);

		if (nHour >= 2 && nHour <= 5) {
			if (nMinute < 11 && nHour==2) {
				calendar.add(Calendar.DATE, -1);
				base_time = "2300";
			}
			base_time = "0200";
		} else if (nHour >= 5 && nHour <= 8) {
			if (nMinute < 11 && nHour == 5) {
				base_time = "0200";
			}
			base_time = "0500";
		} else if (nHour >= 8 && nHour <= 11) {
			if (nMinute < 11 && nHour == 8) {
				base_time = "0500";
			}
			base_time = "0800";
		} else if (nHour >= 11 && nHour <= 14) {
			if (nMinute < 11 && nHour == 11) {
				base_time = "0800";
			}
			base_time = "1100";
		} else if (nHour >= 14 && nHour <= 17) {
			if (nMinute < 11 && nHour == 14) {
				base_time = "1100";
			}
			base_time = "1400";
		} else if (nHour >= 17 && nHour <= 20) {
			if (nMinute < 11 && nHour == 17) {
				base_time = "1400";
			}
			base_time = "1700";
		} else if (nHour >= 20 && nHour <= 23) {
			if (nMinute < 11 && nHour == 20) {
				base_time = "1700";
			}
			base_time = "2000";
		} else if (nHour >= 23) {
			if (nHour == 23 && nMinute > 10) {
				base_time = "2300";
			}
			base_time = "2000";
		} else if (nHour >= 0 && nHour < 2) {
			base_time = "2300";
		}
		 mHour = nHour;
		 base_date = sdf.format(calendar.getTime());
			
	}
}
