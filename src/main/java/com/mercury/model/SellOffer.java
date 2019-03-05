 package com.mercury.model;

import java.time.LocalDateTime;

public class SellOffer extends Offer {	
	
	public SellOffer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId) {
		super(product, listedTerms, dueDate, quantity, ownerId);
	}

	public SellOffer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId, Bid acceptedBid) {
		super(product, listedTerms, dueDate, quantity, ownerId, acceptedBid);
	}
	
}
