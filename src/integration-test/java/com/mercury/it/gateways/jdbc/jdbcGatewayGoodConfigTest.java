package com.mercury.it.gateways.jdbc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercury.gateways.Gateway;
import com.mercury.gateways.jdbc.ProductGatewayJDBC;

public class jdbcGatewayGoodConfigTest {
	
	private Gateway productGateway;
	
	@BeforeEach
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
