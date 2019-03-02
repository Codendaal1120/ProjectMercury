package com.mercury.it.gateways.jdbc;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mercury.gateways.OfferTermsGateway;
import com.mercury.gateways.jdbc.OfferTermsGwatewayJDBC;

public class OfferTermsGatewayJDBCTest {
	
	private OfferTermsGateway termsGateway;
	
	@BeforeClass
	public void setUp() {
		termsGateway = new OfferTermsGwatewayJDBC("jdbc.xml");
	}
	
	@Test
	public void testSaveOfferTerms() {

		//OfferTerms newTerms = new OfferTerms(totalPrice, delivery, payment)
	}
	
}
