package kr.ac.sungkyul.Model.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.Model.DAO.InfoDAO;
import kr.ac.sungkyul.Model.DTO.InfoDTO;
import kr.ac.sungkyul.Utils.DBConnect;

@Repository
public class InfoDAOImpl implements InfoDAO{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int overlap = 0;

	
	public boolean inputData(InfoDTO dto) {
		try {
			con = DBConnect.getConnection();
			pstmt = con.prepareStatement("insert into info values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getImg());
			pstmt.setString(3, dto.getAddr());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getFood());
			pstmt.setString(6, dto.getPrice());
			pstmt.setString(7, dto.getParking());
			pstmt.setString(8, dto.getTime());
			pstmt.setDouble(9, dto.getLat());
			pstmt.setDouble(10, dto.getLng());
			int res = pstmt.executeUpdate();
			if (res > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			overlap += 1;
			System.out.println(e + "\t" + overlap);
		} finally {
			DBConnect.close(con, pstmt);
		}
		return false;
	}

	
	public InfoDTO[] getArroundData(double lat, double lng) {
		ArrayList<InfoDTO> list = new ArrayList<>();
		try {
			con = DBConnect.getConnection();
			pstmt = con.prepareStatement("select * from info where (lat between " + lat + " and " + (lat + 0.001)
					+ ") and (lng between " + lng + " and " + (lng + 0.01) + ")");
			/*pstmt = con.prepareStatement("select * from info where (lat between " + 37.562773 + " and " + (37.562773 + 0.001)
					+ ") and (lng between " + 126.9257723 + " and " + (126.9257723 + 0.01) + ")");*/
			/*pstmt = con.prepareStatement("select * from info where lat=37.562773 and lng=126.9257723");*/
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InfoDTO dto = new InfoDTO();
				dto.setName(rs.getString("name"));
				dto.setImg(rs.getString("img"));
				dto.setAddr(rs.getString("addr"));
				dto.setTel(rs.getString("tel"));
				dto.setFood(rs.getString("food"));
				dto.setPrice(rs.getString("price"));
				dto.setParking(rs.getString("parking"));
				dto.setTime(rs.getString("time"));
				dto.setLat(rs.getDouble("lat"));
				dto.setLng(rs.getDouble("lng"));
				list.add(dto);
			}
			InfoDTO[] dto = new InfoDTO[list.size()];
			list.toArray(dto);
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnect.close(con, pstmt, rs);
		}
		System.out.println("1");
		return null;
	}

}
