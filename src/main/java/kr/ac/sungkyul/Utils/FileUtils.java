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
		ClassPathResource resource = new ClassPathResource("config/GPS.xlsx"); //spring�� ���� ��� ��������
		FileInputStream fis = new FileInputStream(resource.getFile());
		ZipSecureFile.setMinInflateRatio(-1.0d); // ������� ������ xlsx�� ������ ����
		XSSFWorkbook workbook = new XSSFWorkbook(fis); 
		XSSFSheet sheet = workbook.getSheetAt(0); // �Ű��� : ��Ʈ��(1�� = 0) 
		int rows = sheet.getPhysicalNumberOfRows(); // ��Ʈ �� �����ϴ� rows����, �츮�ǰ�� 3773��
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) { // �� ������ŭ �ݺ�
			XSSFRow row = sheet.getRow(rowIndex); // sheet �� �࿡ ����(index�� ����)
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells(); // row���� ����ϴ� cell ����
				for (int columnIndex = 0; columnIndex <= cells; columnIndex++) { // cell������ŭ �ݺ�, �� ������ ������ �̾Ƴ� ����
					XSSFCell cell = row.getCell(columnIndex); // �ε����� �̿��Ͽ� cell ���� ����
					if (cell == null) {
						continue;
					} else {
						//columnIndex 3,4���� ���� nx,ny������ ä��������
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
