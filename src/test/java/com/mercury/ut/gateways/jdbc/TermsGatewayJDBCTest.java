package com.mercury.ut.gateways.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.mercury.gateways.TermsGateway;
import com.mercury.gateways.jdbc.TermsGatewayJDBC;
import com.mercury.model.CashPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.Terms;
import com.mercury.model.PaymentDetails;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TermsGatewayJDBCTest {
	
	private TermsGateway termsGateway;
	
	@Before
	public void setUp() {
		termsGateway = new TermsGatewayJDBC("jdbc.xml");
	}
	
	@Test
	public void testSaveTerms() {
		DeliveryDetails deliveryDetails = new DeliveryDetails(LocalDate.now().plus( 10, ChronoUnit.DAYS ), "some spoof address", DeliveryDetails.DeliveryType.DELIVERY);
		PaymentDetails cashPayment = new CashPayment( LocalDate.now().plus( 5, ChronoUnit.DAYS ) );
		assertTrue(termsGateway.saveTerms( new Terms(3000, deliveryDetails, cashPayment) ) > 1);
	}

	
	@Test
	public void testGetTermsById(){
		assertEquals(1, termsGateway.getTermsById(1).getId());
	}
	
}
