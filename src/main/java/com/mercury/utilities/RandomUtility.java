package com.mercury.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtility {

	public static LocalDate getRandomDate(){
		ChronoUnit[] DateUnits = { ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.WEEKS };
		return LocalDate.now().plus(getRandomInt(0, 10), DateUnits[getRandomInt(0, DateUnits.length-1)]);
	}

	public static LocalDateTime getRandomDateTime(){
		ChronoUnit[] DateTimeUnits = { ChronoUnit.MINUTES, ChronoUnit.SECONDS, ChronoUnit.HOURS };
		return LocalDateTime.now().plus(getRandomInt(0, 50), DateTimeUnits[getRandomInt(0, DateTimeUnits.length-1)]);
	}

	public static int getRandomInt(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static double getRandomDouble(double min, double max){
		return ThreadLocalRandom.current().nextDouble(min, max + 1);
	}

	public static long getRandomLong(long min, long max){
		return ThreadLocalRandom.current().nextLong(min, max + 1);
	}

	public static String getRandomString(int stringLength){
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(stringLength);
		for (int i = 0; i < stringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
			(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

}