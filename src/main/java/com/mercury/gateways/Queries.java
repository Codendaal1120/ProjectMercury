package com.mercury.gateways;

public final class Queries {
	
	public final class Products{
		public final static String getAllProducts = "SELECT id, name FROM tbProducts";
	}
	
	public final class Offers{
		public final static String getAllOffers = "SELECT * FROM tbOffers";
	}
	
	public final class OfferTerms{
		public final static String saveOfferTerms = "INSERT INTO tbOfferTerms (offer_id, total_price, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date) VALUES (?, ?, ?, ?, ?, ?, ?);";	
		//SELECT id, offer_id, price_per_unit, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date, payment_currency_type FROM mercury.tbOfferTerms;
	
	}
	
}
