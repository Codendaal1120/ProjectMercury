 package com.mercury.model;

import java.time.LocalDateTime;

public class BuyOffer extends Offer {	
	
	public BuyOffer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId) {
		super(product, listedTerms, dueDate, quantity, ownerId);
	}
	
	public BuyOffer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId, Bid acceptedBid) {
		super(product, listedTerms, dueDate, quantity, ownerId, acceptedBid);
	}
}
