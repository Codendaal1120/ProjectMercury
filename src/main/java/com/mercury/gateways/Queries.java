package com.mercury.gateways;

public final class Queries {
	
	public final class Products{
		public final static String getAllProducts = "SELECT id, name FROM tbProducts";
	}
	
	public final class Offers{
		public final static String getAllOffers = "SELECT * FROM tbOffers";
	}
	
	public final class Terms{
		public final static String saveTerms = "INSERT INTO tbOfferTerms (total_price, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date) VALUES (?, ?, ?, ?, ?, ?);";	
		public final static String getTermsById = "SELECT id, total_price, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date, payment_currency_type FROM tbOfferTerms WHERE id = ?;";
	}
	
}
