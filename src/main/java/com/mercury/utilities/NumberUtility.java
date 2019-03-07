package com.mercury.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtility {

	public static double Round(double value, int places) {
		if (places < 0){
			places = 0;
		}
		BigDecimal bigDecimal = new BigDecimal(value);
		bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

}