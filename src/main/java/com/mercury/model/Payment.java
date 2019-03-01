package com.mercury.model;

import java.time.LocalDate;

public abstract class Payment {
	
	public String currency = "USD";
	public String currencySymbol = "$";
	
	private LocalDate paymentDueDate;
	
	public LocalDate getPaymentDueDate() {
		return this.paymentDueDate;
	}	
	
	public abstract String getPaymentType();
	
	public Payment(LocalDate paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	
}
