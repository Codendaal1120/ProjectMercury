package com.mercury.model;

import com.mercury.utilities.JsonUtility;

public class OfferTerms {

	private double totalPrice;
	private DeliveryDetails delivery;
	private PaymentDetails payment;
	private long offerId;
	//public String currency;
	
	/***** Functions *****/
	
	public OfferTerms(long offerId, double totalPrice, DeliveryDetails delivery, PaymentDetails payment) {
		this.totalPrice = totalPrice;
		this.delivery = delivery;
		this.payment = payment;
		this.offerId = offerId;
	}
	
	/***** get functions *****/

	public long getOfferId(){
		return offerId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public DeliveryDetails getDelivery() {
		return delivery;
	}	

	public PaymentDetails getPayment() {
		return payment;
	}

	public String getPaymentMethodText() {
		return payment.getPaymentMethodText();
	}
	
	public String toJsonString() {
		return JsonUtility.objectToJson(this);		
	}
	
}
