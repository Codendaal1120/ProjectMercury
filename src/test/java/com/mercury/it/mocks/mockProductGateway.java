package com.mercury.it.mocks;

import java.util.ArrayList;

import com.mercury.model.Product;
import com.mercury.utilities.RandomUtility;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.ProductGateway;

public class mockProductGateway extends Gateway implements ProductGateway {
	
	private Product[] products = { new Product(1, "50 PPM Diesel/Gas oil (Premium)"), new Product(2, "500 PPM Diesel/Gas oil (HSE)"), new Product(3, "HFO"), new Product(4, "95  RON MOGAS/Petrolium") };

	public mockProductGateway(String config) {
		super(config);
	}

	@Override
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product(1, "Product1"));
		mockProducts.add(new Product(2, "Product2"));
		mockProducts.add(new Product(3, "Product3"));
		return mockProducts;
	}
	
	@Override
	public void loadConfig(String config) {	}

	@Override
	public void initializeDatabase() {}

	@Override
	public boolean isInitialized() {
		return false;
	}

	public Product getRandomProduct(){
		return products[RandomUtility.getRandomInt(0, products.length-1)];
	}
	
}
