package kr.ac.sungkyul.Model.DAO;

import kr.ac.sungkyul.Model.DTO.LocationDTO;

public interface LocationDAO {
	public LocationDTO match(String latitude, String longitude);
}
