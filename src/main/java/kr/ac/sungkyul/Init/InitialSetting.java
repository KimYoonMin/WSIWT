package kr.ac.sungkyul.Init;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.ac.sungkyul.Model.DAO.DustDAO;
import kr.ac.sungkyul.Model.DAO.WeatherDAO;


@Component
public class InitialSetting implements InitializingBean{
	@Autowired
	private DustDAO dustDAO;
	@Autowired
	private WeatherDAO weatherDAO;	
	
	/*
	 * @PostConstruct public void init() { System.out.println("InitialSetting");
	 * ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
	 * Runnable dustTh = new ParsingDust(); Runnable weatherTh = new
	 * ParsingWeather(); executor.scheduleAtFixedRate(dustTh, 0, 1, TimeUnit.HOURS);
	 * executor.scheduleAtFixedRate(weatherTh, 0, 3, TimeUnit.HOURS); }
	 */
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitialSetting");
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		Runnable dustTh = new ParsingDust(); 
		Runnable weatherTh = new ParsingWeather(); 
		/*executor.scheduleAtFixedRate(dustTh, 0, 1, TimeUnit.HOURS);
		executor.scheduleAtFixedRate(weatherTh, 0, 3, TimeUnit.HOURS);*/
		
	}
	
	class ParsingDust implements Runnable {
		@Override
		public void run() {
			System.out.println("init dustDAO : " + dustDAO);
			dustDAO.parsingAndSaving();
		}
	}

	class ParsingWeather implements Runnable {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		String nowHour = hourFormat.format(date);

		@Override
		public void run() {
			System.out.println("init weatherDAO : " + weatherDAO);
			weatherDAO.parsingAndSaving();
		}
	}

	
}
