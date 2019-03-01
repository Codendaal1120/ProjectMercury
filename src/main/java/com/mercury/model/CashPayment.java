package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class CashPayment extends Payment {
	
	public CashPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
	}

	@Override
	public String getPaymentType() {
		return "Cash payment on " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
