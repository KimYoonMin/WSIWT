package kr.ac.sungkyul.Model.DTO;

public class DustDTO {
	private String sidoName;
	private String cityName;
	private String dataTime;
	private String pm10Value;
	private String pm25Value;
	private int pm10Grade;		
	private int pm25Grade;
	
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	
	public String getPm10Value() {
		return pm10Value;
	}
	public void setPm10Value(String pm10Value) {
		this.pm10Value = pm10Value;
	}
	public String getPm25Value() {
		return pm25Value;
	}
	public void setPm25Value(String pm25Value) {
		this.pm25Value = pm25Value;
	}
	public int getPm10Grade() {
		return pm10Grade;
	}
	public void setPm10Grade(int pm10Grade) {
		this.pm10Grade = pm10Grade;
	}
	public int getPm25Grade() {
		return pm25Grade;
	}
	public void setPm25Grade(int pm25Grade) {
		this.pm25Grade = pm25Grade;
	}
	@Override
	public String toString() {
		return dataTime+"  "+cityName+"의 미세먼지 수치는"+pm10Value+", 초 미세먼지 수치는 "+pm25Value;
	}
}
