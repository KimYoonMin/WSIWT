package kr.ac.sungkyul.Model.DAO;

import kr.ac.sungkyul.Model.DTO.InfoDTO;

public interface InfoDAO {
	public boolean inputData(InfoDTO dto);
	public InfoDTO[] getArroundData(double lat, double lng);
}
