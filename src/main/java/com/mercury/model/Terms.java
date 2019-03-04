package com.mercury.model;

import com.mercury.utilities.JsonUtility;

public class Terms {

	private long id;
	private double totalPrice;
	private DeliveryDetails delivery;
	private PaymentDetails payment;
	//public String currency;
	
	/***** Functions *****/
	
	public Terms(double totalPrice, DeliveryDetails delivery, PaymentDetails payment) {
		this.totalPrice = totalPrice;
		this.delivery = delivery;
		this.payment = payment;
	}
	
	/***** get functions *****/

	public long getId(){
		return id;
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

	/***** Set functions *****/

	public void setId(long id){
		this.id = id;
	}
	
}
