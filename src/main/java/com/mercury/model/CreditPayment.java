package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class CreditPayment extends Payment{
	
	public CreditPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
	}
	
	@Override
	public String getPaymentType() {
		return "Credit untill " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
