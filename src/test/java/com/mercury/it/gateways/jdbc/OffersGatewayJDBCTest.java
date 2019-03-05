package com.mercury.it.gateways.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import com.mercury.model.CashPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.Offer;
import com.mercury.model.PaymentDetails;
import com.mercury.model.Product;
import com.mercury.model.BuyOffer;
import com.mercury.model.Terms;
import com.mercury.gateways.OffersGateway;
import com.mercury.gateways.jdbc.OffersGatewayJDBC;

public class OffersGatewayJDBCTest {
	
	private OffersGateway offersGateway;
	
	@Before
	public void setUp() {
		offersGateway = new OffersGatewayJDBC("jdbc.xml");
	}	
	
	@Test
	public void testSaveOffer(){
		DeliveryDetails deliveryDetails = new DeliveryDetails(LocalDate.now().plus( 10, ChronoUnit.DAYS ), "some spoof address", DeliveryDetails.DeliveryType.DELIVERY);
		PaymentDetails cashPayment = new CashPayment( LocalDate.now().plus( 5, ChronoUnit.DAYS ) );
		Terms listedTerms = new Terms(3000, deliveryDetails, cashPayment);
		assertTrue( offersGateway.saveOffer(new BuyOffer(new Product(1, "Dummy product"), listedTerms, LocalDateTime.now().plus(10, ChronoUnit.DAYS), 0.0 , 0)) > 0);
	}

	@Test
	public void testGetAllOffers() {
		ArrayList<Offer> offers = offersGateway.getAllOffers();
		assertTrue(offers.size() > 1);		
	}
	
	@Test
	public void testGetOfferById(){
		//assertNotNull(offers.get(0).getProduct());
		//assertNotNull(offers.get(0).getListedTerms());
	}
}
