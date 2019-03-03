package com.mercury.it.gateways.jdbc;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.mercury.gateways.OfferTermsGateway;
import com.mercury.gateways.jdbc.OfferTermsGatewayJDBC;
import com.mercury.model.CashPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.OfferTerms;
import com.mercury.model.PaymentDetails;

public class OfferTermsGatewayJDBCTest {
	
	private OfferTermsGateway termsGateway;
	
	@Before
	public void setUp() {
		termsGateway = new OfferTermsGatewayJDBC("jdbc.xml");
	}
	
	@Test
	public void testSaveOfferTerms() {
		DeliveryDetails deliveryDetails = new DeliveryDetails(LocalDate.now().plus( 10, ChronoUnit.DAYS ), "some spoof address", DeliveryDetails.DeliveryType.DELIVERY);
		PaymentDetails cashPayment = new CashPayment( LocalDate.now().plus( 5, ChronoUnit.DAYS ) );
		assertTrue(termsGateway.saveOfferTerms( new OfferTerms(1, 3000, deliveryDetails, cashPayment) ) > 1);
	}
	
}
