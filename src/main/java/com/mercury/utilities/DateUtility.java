package com.mercury.utilities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	public static Date addDaysToDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		
		return calendar.getTime();
	}
	
	public static String TimeDiffrenceHoursMinutesSeconds(LocalDateTime firstDate, LocalDateTime secondDate) {
		Duration duration = Duration.between(firstDate, secondDate);
		long diffInSecondsTotal = duration.getSeconds();	   
		long diffInHours = diffInSecondsTotal / (60 * 60);
		long diffInMinutes = (diffInSecondsTotal - (diffInHours * 60 *60)) / 60;
		long diffInSeconds = diffInSecondsTotal - (diffInHours * 60 * 60) - (diffInMinutes * 60); 
	
		return diffInHours + ":" + diffInMinutes + ":" + diffInSeconds;
	}
	
	public static String TimeDiffrenceHoursMinutes(LocalDateTime firstDate, LocalDateTime secondDate) {
		Duration duration = Duration.between(firstDate, secondDate);
		long diffInSecondsTotal = duration.getSeconds();		   
		long diffInHours = diffInSecondsTotal / (60 * 60);
		long diffInMinutes = (diffInSecondsTotal - (diffInHours * 60 *60)) / 60;
		
		return diffInHours + ":" + diffInMinutes;
	}
	
	public static String DateToString(LocalDate date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC);		
		return date.format(formatter);
	}	

}
