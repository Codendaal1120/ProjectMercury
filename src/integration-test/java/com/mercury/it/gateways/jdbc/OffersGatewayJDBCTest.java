package com.mercury.it.gateways.jdbc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mercury.application.model.Offer;
import com.mercury.gateways.OffersGateway;
import com.mercury.gateways.jdbc.OffersGatewayJDBC;

public class OffersGatewayJDBCTest {
	
	private OffersGateway offersGateway;
	
	@BeforeEach
	public void setUp() {
		offersGateway = new OffersGatewayJDBC("jdbc.xml");
	}	
	
	@Test
	public void testCanGetOffers() {
		ArrayList<Offer> offers = offersGateway.getAllOffers();
		assertTrue(offers.size() > 1);
	}
	
}
