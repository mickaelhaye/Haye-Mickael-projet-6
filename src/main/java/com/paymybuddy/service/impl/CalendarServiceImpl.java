package com.paymybuddy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import com.paymybuddy.service.CalendarService;

/**
 * CalendarService is the class to recover the date and the hour
 * 
 * @author Mickael Hayé
 */
@Service
public class CalendarServiceImpl implements CalendarService {

	/**
	 * recover the date
	 */
	@Override
	public String getDate() {
		// récupération date actuelle
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String formatDate = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		String dateActuelle = sdf.format(calendar.getTime());
		return dateActuelle;
	}

	/**
	 * recover the hour
	 */
	@Override
	public String getHour() {
		// récupération date actuelle
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String formatDate = "hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		String heureActuelle = sdf.format(calendar.getTime());
		return heureActuelle;
	}

}
