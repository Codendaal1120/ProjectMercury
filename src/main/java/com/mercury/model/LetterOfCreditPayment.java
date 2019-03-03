package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.DateUtility;

public class LetterOfCreditPayment extends PaymentDetails {
	
	public LetterOfCreditPayment(LocalDate paymentDueDate) {
		super(paymentDueDate);
		this.PaymentMethod = PaymentDetails.PaymentType.LETTER_OF_CREDIT;
	}
	
	@Override
	public String getPaymentMethodText() {
		return "LC on " + DateUtility.DateToString(getPaymentDueDate(), "d MMMM yyyy");
	}
	
}
