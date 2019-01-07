package kr.ac.sungkyul.Model.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.Model.DTO.LocationDTO;



@Repository
public class LocationDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public LocationDTO match(String latitude, String longitude) {
		LocationDTO dto=null;
		Criteria criteria=new Criteria("latitude");
		criteria.lte(Double.parseDouble(latitude)+0.01).gt(Double.parseDouble(latitude)-0.01);
		System.out.println("latitude : "+latitude);
		criteria.and("longitude").gt(Double.parseDouble(longitude)-0.01).lte(Double.parseDouble(longitude)+0.01);
		System.out.println("longitude" + longitude);
		Query query=new Query(criteria);
		dto=mongoTemplate.findOne(query, LocationDTO.class,"location");
		System.out.println(dto.getLatitude());
		System.out.println(dto.getCity());
		System.out.println(dto.getGu());
		System.out.println(dto.getDong());
		return dto;
	}
}
