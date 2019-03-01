package com.mercury.it.gateways.jdbc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercury.gateways.Gateway;
import com.mercury.gateways.jdbc.ProductGatewayJDBC;

public class jdbcGatewayBadConfigTest {
	
	private Gateway productGateway;
	
	@BeforeEach
	public void setUp() {
		productGateway = new ProductGatewayJDBC("nonexistantfile");
	}	
	
	@Test
	public void testCannotLoadConfigFile() {
		assertFalse(productGateway.isConfigLoaded());
	}
	
	@Test
	public void testCannotDbInitialize() {
		productGateway.initializeDatabase();
		assertFalse(productGateway.isInitialized());
	}
	
}
