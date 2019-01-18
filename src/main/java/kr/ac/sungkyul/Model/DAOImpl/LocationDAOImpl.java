package kr.ac.sungkyul.Model.DAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.Model.DAO.LocationDAO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;



@Repository
public class LocationDAOImpl implements LocationDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public LocationDTO match(String latitude, String longitude) {
		LocationDTO dto=null;
		Criteria criteria=new Criteria("latitude");
		//임시로 넣었다.
		criteria.lte(Double.parseDouble("37.3799143")+0.01).gt(Double.parseDouble("37.3799143")-0.01);
		System.out.println("latitude : "+latitude);
		//임시로 넣었다.
		criteria.and("longitude").gt(Double.parseDouble("126.9286913")-0.01).lte(Double.parseDouble("126.9286913")+0.01);
		System.out.println("longitude : " + longitude);
		Query query=new Query(criteria);
		dto=mongoTemplate.findOne(query, LocationDTO.class,"location");
		System.out.println("dto : "+dto);
		System.out.println(dto.getLatitude());
		System.out.println(dto.getCity());
		System.out.println(dto.getGu());
		System.out.println(dto.getDong());
		return dto;
	}
}
