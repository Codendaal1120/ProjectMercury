package com.mercury.ut.mocks;

import java.util.ArrayList;

import com.mercury.model.Product;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.ProductGateway;

public class mockProductGateway extends Gateway implements ProductGateway {
	
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
	
}
