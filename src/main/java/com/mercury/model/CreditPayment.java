package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class CreditPayment extends PaymentDetails{
	
	public CreditPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
		this.PaymentMethod = PaymentDetails.PaymentType.CREDIT;
	}
	
	@Override
	public String getPaymentMethodText() {
		return "Credit untill " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
