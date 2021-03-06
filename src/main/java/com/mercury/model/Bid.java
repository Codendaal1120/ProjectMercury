package com.mercury.model;

public class Bid {

	private long id;
	private long ownerId;
	private long offerId;
	private Terms terms;
	
	public Bid(Terms terms, long offerId, long ownerId) {
		this.offerId = offerId;
		this.ownerId = ownerId;
		if (terms == null){
			throw new NullPointerException("Terms cannot be null");
		}
		else{
			this.terms = terms; 
		}
	}

	/***** get functions *****/
	
	public long getOfferId() {
		return offerId;
	}

	public long getId(){
		return this.id;
	}

	public long getOwnerId(){
		return ownerId;
	}
	
	public double getTotalPrice() {
		return terms.getTotalPrice();
	}

	public Terms getTerms(){
		return terms;
	}
	
	public DeliveryDetails getDelivery() {
		return terms.getDelivery();
	}

	public String getPaymentType() {
		return terms.getPaymentMethodText();
	}

	/***** set functions *****/
	
	public void setId(long id){
		this.id = id;
	}

	/***** Override functions *****/
	
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
		}
		else if (!Bid.class.isAssignableFrom(obj.getClass())) {
            return false;
		}
		final Bid otherBid = (Bid)obj;
		if (otherBid.getId() == getId()){
			return true;
		}
		else{
			return false;
		}
	}
}
