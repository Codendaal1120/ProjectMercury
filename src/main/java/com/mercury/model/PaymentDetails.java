package com.mercury.model;

import java.time.LocalDate;

public abstract class PaymentDetails {

	public String currency = "USD";
	public String currencySymbol = "$";
	private LocalDate paymentDueDate;

	public PaymentDetails(LocalDate paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	
	public LocalDate getPaymentDueDate() {
		return this.paymentDueDate;
	}	

	public String getPaymentCurrency(){
		return currency;
	}

	public String getPaymentCurrencySymbol(){
		return currencySymbol;
	}
	
	public abstract String getPaymentMethodText();
	
}
