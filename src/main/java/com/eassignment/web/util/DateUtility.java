package com.eassignment.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateUtility {
	
	private final Logger LOGGER  = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public Calendar parseStringDate(String sDate){
		
		Calendar calendar = Calendar.getInstance();
		
		Date date;
		try {
			date = format.parse(sDate);
			calendar.setTime(date);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return calendar;
	}

}
