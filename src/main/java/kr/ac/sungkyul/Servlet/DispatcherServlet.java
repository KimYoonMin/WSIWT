package kr.ac.sungkyul.Servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import kr.ac.sungkyul.Model.DAO.DustDAO;
import kr.ac.sungkyul.Model.DAO.WeatherDAO;

public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {
	@Autowired
	private DustDAO dustDAO;
	@Autowired
	private WeatherDAO weatherDAO;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dustDAO = (DustDAO) super.getWebApplicationContext().getBean("dustDAO");
		weatherDAO = (WeatherDAO) super.getWebApplicationContext().getBean("weatherDAO");
		System.out.println("Custom DispatcherServlet load, init method is called");
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		Runnable dustTh = new ParsingDust();
		Runnable weatherTh = new ParsingWeather();
		executor.scheduleAtFixedRate(dustTh, 0, 1, TimeUnit.HOURS);
		executor.scheduleAtFixedRate(weatherTh, 0, 3, TimeUnit.HOURS);
	}

	class ParsingDust implements Runnable {
		@Override
		public void run() {
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
			weatherDAO.parsingAndSaving(date);
		}
	}
}
