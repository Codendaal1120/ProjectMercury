package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class LetterOfCreditPayment extends Payment {
	
	public LetterOfCreditPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
	}
	
	@Override
	public String getPaymentType() {
		return "LC on " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
