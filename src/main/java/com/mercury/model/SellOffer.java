 package com.mercury.model;

import java.time.LocalDateTime;

public class SellOffer extends Offer {	
	
	public SellOffer(Product product, OfferTerms listedTerms, LocalDateTime dueDate, double quantity) {
		super(product, listedTerms, dueDate, quantity);
	}
	
}
