package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class CashPayment extends PaymentDetails {
	
	public CashPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
		this.PaymentMethod = PaymentDetails.PaymentType.CASH;
	}

	@Override
	public String getPaymentMethodText() {
		return "Cash payment on " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
