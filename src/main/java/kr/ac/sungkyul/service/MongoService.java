package kr.ac.sungkyul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.sungkyul.Model.DAO.LocationDAO;
import kr.ac.sungkyul.Model.DAO.WeatherDAO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;
import kr.ac.sungkyul.Model.DTO.WeatherDTO;

@Service
public class MongoService {

	@Autowired
	private LocationDAO ldao;
	@Autowired
	private WeatherDAO wdao;

	public LocationDTO match(String x, String y) {
		return ldao.match(x, y);
	}

	public WeatherDTO find(String nx, String ny) {
		return wdao.findWeather(nx, ny);
	}

}
