package com.mercury.gateways;

public final class Queries {
	
	public final class Products{
		public final static String getAllProducts = "SELECT id, name FROM tbProducts";
	}
	
	public final class Offers{
		public final static String getAllOffers = "SELECT o.id, o.owner_id, o.offer_type, o.qty, o.status, o.due_date, o.listed_terms, o.accepted_terms, o.product_id, " +
		"l.id AS listed_id, l.total_price AS listed_total_price, l.delivery_type AS listed_delivery_type, l.delivery_address AS listed_delivery_address, l.delivery_date AS listed_delivery_date, l.payment_type AS listed_payment_type, l.payment_due_date AS listed_payment_due_date, l.payment_currency_type AS listed_payment_currency_type,  " +
		"a.id AS accepted_id, a.total_price AS accepted_total_price, a.delivery_type AS accepted_delivery_type, a.delivery_address AS accepted_delivery_address, a.delivery_date AS accepted_delivery_date, a.payment_type AS accepted_payment_type, a.payment_due_date AS accepted_payment_due_date, a.payment_currency_type AS accepted_payment_currency_type, " +
		"p.id AS product_id, p.name AS product_name " +
		"FROM tbOffers o " +
		"INNER JOIN tbProducts p ON p.id = o.product_id " +
		"INNER JOIN tbTerms l ON l.id = o.listed_terms  " +
		"LEFT JOIN tbTerms a ON a.id = o.accepted_terms " +
		"WHERE o.deleted_on IS NULL;";
		public final static String saveOffer = "INSERT INTO tbOffers(owner_id, offer_type, product_id, qty, status, due_date, listed_terms, accepted_terms)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	public final class Terms{
		public final static String saveTerms = "INSERT INTO tbTerms (total_price, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date) VALUES (?, ?, ?, ?, ?, ?);";	
		public final static String getTermsById = "SELECT id, total_price, delivery_type, delivery_address, delivery_date, payment_type, payment_due_date, payment_currency_type FROM tbTerms WHERE id = ?;";
	}
}
