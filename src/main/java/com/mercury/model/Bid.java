package com.mercury.model;

public class Bid {
	
	public long id;
	public long ownerId;
	
	private long offerId;
	private OfferTerms terms;
	
	public Bid(OfferTerms terms, long offerId) {
		this.terms = terms; 
		this.offerId = offerId;
	}
	
	public long getOfferId() {
		return offerId;
	}
	
	public double getTotalPrice() {
		return terms.getTotalPrice();
	}
	
	public DeliveryDetails getDelivery() {
		return terms.getDelivery();
	}

	public String getPaymentType() {
		return terms.getPaymentMethodText();
	}
	
}
