package kr.ac.sungkyul.Controller.Impl;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.ac.sungkyul.Controller.Interface.BreakFastController;
import kr.ac.sungkyul.Model.DAO.LocationDAO;
import kr.ac.sungkyul.Model.DAO.WeatherDAO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;
import kr.ac.sungkyul.Model.DTO.WeatherDTO;

@Controller
public class BreakFastControllerImpl implements BreakFastController{
	/*@Autowired
	private MongoService service;*/
	@Autowired
	private LocationDAO ldao;
	@Autowired
	private WeatherDAO wdao;
	
	@Override
	@RequestMapping(value="/")
	public ModelAndView expressAll(ModelAndView mav,Object ob) {
		mav.setViewName("test");
		return mav;
	}
	@RequestMapping(value="/home.do")
	public ModelAndView list(ModelAndView mav, HttpServletRequest req) {
		HttpSession session = req.getSession();
		DecimalFormat format=new DecimalFormat();
		format.applyLocalizedPattern("0.0000");
		String latitude=format.format(Double.parseDouble(req.getParameter("latitude")));
		String longitude=format.format(Double.parseDouble(req.getParameter("longitude")));
		System.out.println(latitude);	
		System.out.println(longitude);
		
		session.setAttribute("latitude", latitude);
		session.setAttribute("longitude", longitude);
		/*LocationDTO dto=service.match(latitude,logitude);*/
		LocationDTO dto=ldao.match(latitude, longitude);
		//model.addAttribute("lo",dto);
		mav.addObject("lo",dto);
		System.out.println("cont : "+dto.getNx()+":"+dto.getNy());
		WeatherDTO wdto=wdao.findWeather(dto.getNx(), dto.getNy());
		mav.addObject("weather",wdto);
		/*service.find(Integer.parseInt(dto.getNx()), Integer.parseInt(dto.getNy()));*/
		//return "home";
		mav.setViewName("home");
		return mav;
	}
	
	
}
