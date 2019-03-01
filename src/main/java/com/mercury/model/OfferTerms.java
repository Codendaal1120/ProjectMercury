package com.mercury.model;

public class OfferTerms {

	private double totalPrice;
	private DeliveryDetails delivery;
	private Payment payment;
	//public String currency;
	
	/***** Functions *****/
	
	public OfferTerms(double totalPrice, DeliveryDetails delivery, Payment payment) {
		this.totalPrice = totalPrice;
		this.delivery = delivery;
		this.payment = payment;
	}
	
	/***** get functions *****/

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public DeliveryDetails getDelivery() {
		return delivery;
	}	
	
	public String getPaymentType() {
		return payment.getPaymentType();
	}
	
}
