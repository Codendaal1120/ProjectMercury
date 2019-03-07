package com.mercury.ut.gateways.jdbc;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import com.mercury.model.Product;
import org.junit.Before;
import org.junit.Test;
import com.mercury.gateways.ProductGateway;
import com.mercury.gateways.jdbc.ProductGatewayJDBC;

public class ProductGatewayJDBCTest {
	
	private ProductGateway productGateway;
	
	@Before
	public void setUp() {
		productGateway = new ProductGatewayJDBC("jdbc.xml");
	}	
	
	@Test
	public void testGetProducts() {
		ArrayList<Product> products = productGateway.getAllProducts();
		assertTrue(products.size() > 1);
	}
	
}
