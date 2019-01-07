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
	//UUU ���� �ٶ����� (��+,��-)
	//VEC ǳ��
	//VVV ���� �ٶ����� (��+,��-)
	//WSD ǳ��
	//POP ����Ȯ��%       <�ʿ�>              
	//PTY �������� ����(0), ��(1), ��/�� ��������(2), ��(3) <�ʿ�>
	//REH ����% 
	//SKY �ϴû���, ����(1),��������(2),��������(3),�帲(4) <�ʿ�>
	//T3H 3�ð���� <�ʿ�>
	//TMX �� �ְ��� <�ʿ�>
	//TMN ��ħ ������� <�ʿ�>
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
