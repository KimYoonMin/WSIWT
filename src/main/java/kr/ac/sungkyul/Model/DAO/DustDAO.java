package kr.ac.sungkyul.Model.DAO;

import java.io.IOException;

import org.json.JSONArray;

import kr.ac.sungkyul.Model.DTO.DustDTO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;

public interface DustDAO {
	public void parsingAndSaving();
	public JSONArray getDustAPI(int i) throws IOException;
	public void analysisJsonArray(JSONArray jArray, String sidoName);
	public void insertDataIntoDB(DustDTO dustDTO);
	public int setPm10GradeVal(String pm10Value);
	public int setPm25GradeVal(String pm25Value);
	public DustDTO findDust(LocationDTO dto);
}
