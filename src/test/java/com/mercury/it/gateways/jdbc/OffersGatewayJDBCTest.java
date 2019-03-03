package com.mercury.it.gateways.jdbc;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import com.mercury.model.Offer;
import com.mercury.gateways.OffersGateway;
import com.mercury.gateways.jdbc.OffersGatewayJDBC;

public class OffersGatewayJDBCTest {
	
	private OffersGateway offersGateway;
	
	@Before
	public void setUp() {
		offersGateway = new OffersGatewayJDBC("jdbc.xml");
	}	
	
	@org.junit.Test
	public void testCanGetAllOffers() {
		ArrayList<Offer> offers = offersGateway.getAllOffers();
		assertTrue(offers.size() > 1);
	}
	
}
