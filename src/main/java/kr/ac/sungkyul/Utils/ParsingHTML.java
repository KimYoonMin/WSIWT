package kr.ac.sungkyul.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.ac.sungkyul.Model.DAO.InfoDAO;
import kr.ac.sungkyul.Model.DTO.InfoDTO;



public class ParsingHTML {
	public static void main(String[] args) {
		List<InfoDTO> list = new ArrayList<InfoDTO>();
		InfoDAO dao = new InfoDAO();
		String path = "C:/Crawl";
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();
		for (File tempFile : fileList) {
			InfoDTO dto = setDTO(tempFile);
			boolean res = dao.inputData(dto);
		}
		System.out.println(list.size());
	}

	public static InfoDTO setDTO(File file) {
		InfoDTO dto = new InfoDTO();
		Document doc = null;
		Hashtable<String, Element> hashtable = new Hashtable<String, Element>();

		try {
			doc = Jsoup.parse(new File("C:/Crawl/" + file.getName()), "UTF-8");
			hashtable.put("�̸�", doc.select(".restaurant_name").get(0));
			hashtable.put("�̹���", doc.select(".center-croping").get(0));
			for (int i = 1; i < 7; i++) {
				try {
					String key = doc.select(".info > tbody > tr:nth-child(" + i + ") > th").get(0).text();
					Element value = doc.select(".info > tbody > tr:nth-child(" + i + ") > td").get(0);
					hashtable.put(key, value);
				} catch (IndexOutOfBoundsException e) {
					String key = doc.select(".no_menu > tbody > tr:nth-child(" + i + ") > th").get(0).text();
					Element value = doc.select(".no_menu > tbody > tr:nth-child(" + i + ") > td").get(0);
					hashtable.put(key, value);
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				String addr = hashtable.get("�ּ�").text();
				Geocoding geocoding = new Geocoding(addr);
				
				dto.setName(hashtable.get("�̸�").text());
				dto.setImg(hashtable.get("�̹���").attr("src").toString());
				dto.setAddr(addr);
				dto.setTel(hashtable.get("��ȭ��ȣ").text());
				dto.setFood(hashtable.get("���� ����").text());
				dto.setPrice(hashtable.get("���ݴ�").text());
				dto.setParking(hashtable.get("����").text());
				dto.setTime(hashtable.get("�����ð�").text());
				dto.setLat(geocoding.latlng[0]);
				dto.setLng(geocoding.latlng[1]);
			} catch (Exception e) {

			}
		}
		return dto;
	}

}
