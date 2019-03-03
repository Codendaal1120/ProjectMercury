package com.mercury.model;

import java.time.LocalDate;

public abstract class PaymentDetails {

	public static final class PaymentType{
		public static final int CASH = 1;
		public static final int LETTER_OF_CREDIT = 2;
		public static final int CREDIT = 1;	
	}

	public String currency = "USD";
	public String currencySymbol = "$";
	protected int PaymentMethod = 0;
	private LocalDate paymentDueDate;

	public PaymentDetails(LocalDate paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	
	public LocalDate getPaymentDueDate() {
		return this.paymentDueDate;
	}	

	public int getPaymentMethod(){
		return this.PaymentMethod;
	}

	public String getPaymentCurrency(){
		return currency;
	}

	public String getPaymentCurrencySymbol(){
		return currencySymbol;
	}
	
	public abstract String getPaymentMethodText();
	
}
