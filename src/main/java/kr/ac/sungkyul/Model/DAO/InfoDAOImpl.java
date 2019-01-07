package kr.ac.sungkyul.Model.DAO;

import java.util.ArrayList;

import kr.ac.sungkyul.Model.DTO.InfoDTO;

public interface InfoDAOImpl {
	public boolean inputData(InfoDTO dto);
	public ArrayList<InfoDTO> getArroundData(Double lat, Double lng); 
}
