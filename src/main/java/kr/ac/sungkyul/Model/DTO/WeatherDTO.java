package kr.ac.sungkyul.Model.DTO;

import java.util.Hashtable;

public class WeatherDTO {
	private String fcstDate;
	private String fcstTime;
	private String baseTime;
	private Hashtable<String,String> category;
	private String nx;
	private String ny;
	
	//category
	//UUU 동서 바람성분 (동+,서-)
	//VEC 풍향
	//VVV 남북 바람성분 (북+,남-)
	//WSD 풍속
	//POP 강수확률%       <필요>              
	//PTY 강수형태 없음(0), 비(1), 비/눈 진눈개비(2), 눈(3) <필요>
	//REH 습도% 
	//SKY 하늘상태, 맑음(1),구름조금(2),구름많음(3),흐림(4) <필요>
	//T3H 3시간기온 <필요>
	//TMX 낮 최고기온 <필요>
	//TMN 아침 최저기온 <필요>
	@Override
	public String toString() {
		return fcstDate+"///"+fcstTime+"///"+nx+"/"+ny+"";
	}

	public String getFcstDate() {
		return fcstDate;
	}

	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}

	public String getFcstTime() {
		return fcstTime;
	}

	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}

	public String getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}

	public Hashtable<String, String> getCategory() {
		return category;
	}

	public void setCategory(Hashtable<String, String> category) {
		this.category = category;
	}

	public String getNx() {
		return nx;
	}

	public void setNx(String nx) {
		this.nx = nx;
	}

	public String getNy() {
		return ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
	}
	
}
