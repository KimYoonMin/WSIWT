package kr.ac.sungkyul.Model.DAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.json.JSONArray;

import kr.ac.sungkyul.Model.DTO.WeatherDTO;

public interface WeatherDAO {
	public void parsingAndSaving();

	public JSONArray getWeatherAPI(String base_date, String base_time, int nx, int ny)
			throws UnsupportedEncodingException, IOException;
	
	public void insertDataIntoDB(WeatherDTO weatherDTO);
	public WeatherDTO findWeather(String nx, String ny);
}
