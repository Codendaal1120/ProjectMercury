package com.mercury.ut.gateways;

import static org.junit.Assert.assertFalse;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.jdbc.ProductGatewayJDBC;
import org.junit.Before;
import org.junit.Test;

public class jdbcGatewayBadConfigTest {
	
	private Gateway productGateway;
	
	@Before
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
