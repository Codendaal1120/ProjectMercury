package com.mercury.gateways;

public final class Queries {
	
	public final class Products{
		public final static String getAllProducts = "SELECT id, name FROM tbProducts";
	}
	
	public final class Offers{
		public final static String getAllOffers = "SELECT * FROM tbOffers";
	}
	
	public final class DeliveryDetails{
		public final static String saveDeliveryDetails = "INSERT INTO tbDeliveryDetails (delivery_type, address, delivery_date) VALUES (?, ?, ?);";
	}
	
}
