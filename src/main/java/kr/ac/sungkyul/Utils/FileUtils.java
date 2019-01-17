package kr.ac.sungkyul.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

public class FileUtils {
	public static HashSet<String> readAndMakeSetFromExcel() throws IOException{
		HashSet<String> excelnXnYDataSet = new HashSet<String>();
		StringBuilder value = new StringBuilder(); 
		ClassPathResource resource = new ClassPathResource("config/GPS.xlsx"); //spring식 파일 경로 가져오기
		FileInputStream fis = new FileInputStream(resource.getFile());
		ZipSecureFile.setMinInflateRatio(-1.0d); // 사용하지 않으면 xlsx이 열리지 않음
		XSSFWorkbook workbook = new XSSFWorkbook(fis); 
		XSSFSheet sheet = workbook.getSheetAt(0); // 매개값 : 시트수(1개 = 0) 
		int rows = sheet.getPhysicalNumberOfRows(); // 시트 내 존재하는 rows갯수, 우리의경우 3773개
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) { // 행 갯수만큼 반복
			XSSFRow row = sheet.getRow(rowIndex); // sheet 내 행에 접근(index를 통해)
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells(); // row에서 사용하는 cell 갯수
				for (int columnIndex = 0; columnIndex <= cells; columnIndex++) { // cell갯수만큼 반복, 각 셀마다 데이터 뽑아낼 것임
					XSSFCell cell = row.getCell(columnIndex); // 인덱스를 이용하여 cell 값에 접근
					if (cell == null) {
						continue;
					} else {
						//columnIndex 3,4번은 격자 nx,ny값으로 채워져있음
					 if (columnIndex == 3) {
							value.append(cell.toString() + "@");
						} else if (columnIndex == 4) {
							value.append(cell.toString());
						}
					}
				}
				excelnXnYDataSet.add(value.toString());
				value.setLength(0);
			}
			
		}
		return excelnXnYDataSet;
	}
	
}
