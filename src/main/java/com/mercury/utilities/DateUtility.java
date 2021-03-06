package com.mercury.utilities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	public static final String sqlDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String sqlDateFormat = "yyyy-MM-dd";

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
		if (diffInSecondsTotal - (diffInHours * 3600) - (diffInMinutes * 60) > 50){
			if (diffInMinutes == 59){
				diffInMinutes = 0;
				diffInHours++;
			}
			else{
				diffInMinutes++;
			}			
		}
		return diffInHours + ":" + diffInMinutes;
	}
	
	public static String DateToString(LocalDate date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC);	
		return date.format(formatter);
	}	

	public static LocalDate StringToDate(String date, String format) {
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern( format );
		return LocalDate.parse(date, dateTimeFormat);
	}	

	public static LocalDateTime StringToDateTime(String date, String format) {
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern( format );
		return LocalDateTime.parse(date, dateTimeFormat);
	}	

}
