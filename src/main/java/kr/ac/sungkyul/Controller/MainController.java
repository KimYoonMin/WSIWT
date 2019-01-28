package kr.ac.sungkyul.Controller;

import java.io.File;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import kr.ac.sungkyul.Model.DAO.DustDAO;
import kr.ac.sungkyul.Model.DAO.InfoDAO;
import kr.ac.sungkyul.Model.DAO.LocationDAO;
import kr.ac.sungkyul.Model.DAO.WeatherDAO;
import kr.ac.sungkyul.Model.DTO.DustDTO;
import kr.ac.sungkyul.Model.DTO.InfoDTO;
import kr.ac.sungkyul.Model.DTO.LocationDTO;
import kr.ac.sungkyul.Model.DTO.WeatherDTO;
import kr.ac.sungkyul.Utils.BasicCrawler;

@Controller
public class MainController{
	/*@Autowired
	private MongoService service;*/
	@Autowired
	private LocationDAO ldao;
	@Autowired
	private WeatherDAO wdao;
	@Autowired
	private DustDAO ddao;
	@Autowired
	private InfoDAO idao;
	
	
	@RequestMapping(value="/")
	public ModelAndView expressAll(ModelAndView mav,Object ob, HttpServletRequest req) {
		String crawl_result = (String)req.getAttribute("crawl_result");
		if(crawl_result != null &&(crawl_result.trim().equals("success") || crawl_result == "success")) {
			mav.addObject("crawl", "crawl_success");
		}
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
		DustDTO ddto = ddao.findDust(dto);
		InfoDTO[] idto = idao.getArroundData(60, 127);
		for(InfoDTO dto_food : idto) {
			System.out.println("dto_food.getName() : "+dto_food.getName());
			System.out.println("dto_food.getImg() : "+dto_food.getImg());
			System.out.println("dto_food.getTel() : "+dto_food.getTel());
		}
		System.out.println("idto : "+idto);
		mav.addObject("weather", wdto);
		mav.addObject("dust", ddto);
		mav.addObject("food", idto);
		/*service.find(Integer.parseInt(dto.getNx()), Integer.parseInt(dto.getNy()));*/
		//return "home";
		mav.setViewName("home");
		return mav;
	}	
	@RequestMapping(value="/crawl.do")
	public ModelAndView crawl(ModelAndView mav, HttpServletRequest req) throws Exception {
		File file = new File("C:/Crawl");
		if(!(file.exists())){
			file.mkdirs();
		}
		String crawlStorageFolder = "C:/Crawl";
		int numberOfCrawlers = 10000;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(10);
		config.setMaxDepthOfCrawling(32766);
		config.setMaxPagesToFetch(1000000000);
		config.setIncludeBinaryContentInCrawling(false);
		config.setResumableCrawling(false);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed("http://www.mangoplate.com");
		BasicCrawler.configure(crawlStorageFolder);
		controller.start(BasicCrawler.class, numberOfCrawlers);
		mav.addObject("crawl_result", "success");
		mav.setViewName("/");
		return mav;
	}
	
}
