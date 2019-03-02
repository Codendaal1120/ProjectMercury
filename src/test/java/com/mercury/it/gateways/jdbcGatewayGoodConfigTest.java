package com.mercury.it.gateways;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.mercury.gateways.Gateway;
import com.mercury.gateways.jdbc.ProductGatewayJDBC;

public class jdbcGatewayGoodConfigTest {
	
	private Gateway productGateway;
	
	@Before
	public void setUp() {
		productGateway = new ProductGatewayJDBC("jdbc.xml");
	}	
	
	@Test
	public void testCanLoadConfigFile() {		
		assertTrue(productGateway.isConfigLoaded());
	}
	
	@Test
	public void testCanDbInitialize() {		
		productGateway.initializeDatabase();
		assertTrue(productGateway.isInitialized());
	}

}
