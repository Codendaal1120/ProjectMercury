package com.mercury.ut.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import com.mercury.model.Bid;
import com.mercury.model.CreditPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.Offer;
import com.mercury.model.OfferTerms;
import com.mercury.model.Payment;
import com.mercury.model.SellOffer;
import org.junit.Before;
import org.junit.Test;

public class OfferTest {
	
	private LocalDateTime futureDate;
	private LocalDateTime pastDate;
	private Offer testOffer;
	
	@Before
	public void setUp() {
		futureDate = LocalDateTime.now().plus(10, ChronoUnit.DAYS);
		pastDate = LocalDateTime.now().plus(-10, ChronoUnit.DAYS);
		testOffer = new SellOffer(null, null, futureDate, 0);
	}
	
	@Test
	public void testNewOfferShouldHaveNewStatus() {		
		assertEquals(Offer.OfferStatus.NEW, testOffer.getStatus());		
	}
	
	@Test
	public void testOpenOfferShouldHaveOpenStatus() {		
		testOffer.setId(1);
		assertEquals(Offer.OfferStatus.OPEN, testOffer.getStatus());		
	}
	
	@Test
	public void testAcceptingBidShouldChangeStatus() {
		testOffer.setId(1);
		testOffer.acceptBid(new Bid(new OfferTerms(0, null, null), testOffer.getId()));
		assertEquals(Offer.OfferStatus.CLOSED, testOffer.getStatus());		
	}
	
	@Test
	public void testOfferShouldBeExpired() {
		testOffer = new SellOffer(null, null, pastDate, 0);
		assertTrue(testOffer.isExpired());
	}
	
	@Test
	public void testOfferShouldNotBeExpired() {
		assertTrue(!testOffer.isExpired());
	}
	
	@Test
	public void testOfferTimeRemaining() {
		assertEquals("240:0", testOffer.getTimeRemaining());
	}
	
	@Test
	public void testOfferPricePerUnit() {	
		OfferTerms testTerms = new OfferTerms(760, null, null);
		Offer testOffer = new SellOffer(null, testTerms, futureDate, 200.00);
		assertEquals(3.8, testOffer.getUnitPrice(), 0.0d);		
	}
	
	@Test
	public void testOfferFees() {		
		assertListedFees(825000, 5000, 2475.0);
		assertListedFees(200000.00, 10001, 570.0);		
		assertListedFees(10001.00, 20001, 27.20272);
		assertListedFees(80000.00, 30001, 217.6);
		assertListedFees(10000.00, 50000, 25);
		assertListedFees(1.00, 50000, 0.0025);
	}
	
	@Test
	public void testAcceptingBidShouldChangeFees() {
		assertAcceptedFees(825000, 5000, 2475.0);
		assertAcceptedFees(200000.00, 10001, 570.0);		
		assertAcceptedFees(10001.00, 20001, 27.20272);
		assertAcceptedFees(80000.00, 30001, 217.6);
		assertAcceptedFees(10000.00, 50000, 25);
		assertAcceptedFees(1.00, 50000, 0.0025);
	}	
	
	@Test
	public void testAcceptingBidShouldChangePrice() {
		OfferTerms originalTerms = new OfferTerms(10000, null, null);
		testOffer = new SellOffer(null, originalTerms, futureDate, 0);
		testOffer.setId(1);
		
		OfferTerms bidTerms = new OfferTerms(50000, null, null);
		Bid testBid = new Bid(bidTerms, testOffer.getId());
		testOffer.acceptBid(testBid);
		
		assertEquals(50000, testOffer.getTotalPrice(), 0.0d);
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryAddress(){
		String testDeliveryAddress = "Some other address";		
		
		DeliveryDetails originalDelivery = new DeliveryDetails(null, "Some address", 0);
		OfferTerms originalTerms = new OfferTerms(0, originalDelivery, null);
		testOffer = new SellOffer(null, originalTerms, null, 0);
		testOffer.setId(1);
		
		DeliveryDetails bidDelivery = new DeliveryDetails(null, testDeliveryAddress, 0);
		OfferTerms bidTerms = new OfferTerms(0, bidDelivery, null);
		Bid testBid = new Bid(bidTerms, testOffer.getId());
		testOffer.acceptBid(testBid);
		
		assertEquals(testDeliveryAddress, testOffer.getDeliveryAddress());
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryDate() throws ParseException {
		LocalDate testDeliveryDate = LocalDate.parse("2019-12-05");			
		
		DeliveryDetails originalDelivery = new DeliveryDetails(LocalDate.parse("2019-01-12"), null, 0);
		OfferTerms originalTerms = new OfferTerms(0, originalDelivery, null);
		testOffer = new SellOffer(null, originalTerms, futureDate, 0);
		testOffer.setId(1);
		
		DeliveryDetails bidDelivery = new DeliveryDetails(testDeliveryDate, null, 0);
		OfferTerms bidTerms = new OfferTerms(0, bidDelivery, null);
		Bid testBid = new Bid(bidTerms, testOffer.getId());
		testOffer.acceptBid(testBid);
		
		assertTrue(testDeliveryDate.compareTo(testOffer.getDeliveryDate()) == 0);
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryType() {
		int testDeliveryType = DeliveryDetails.DeliveryType.PICKUP;
		
		DeliveryDetails originalDelivery = new DeliveryDetails(null, null, DeliveryDetails.DeliveryType.DELIVERY);
		OfferTerms originalTerms = new OfferTerms(0, originalDelivery, null);
		testOffer = new SellOffer(null, originalTerms, futureDate, 0);
		testOffer.setId(1);
		
		DeliveryDetails bidDelivery = new DeliveryDetails(null, null, testDeliveryType);
		OfferTerms bidTerms = new OfferTerms(0, bidDelivery, null);
		Bid testBid = new Bid(bidTerms, testOffer.getId());
		testOffer.acceptBid(testBid);
		
		assertEquals(testDeliveryType, testOffer.getDeliveryType());
	}	
	
	@Test
	public void testAcceptingBidShouldChangePaymentType() throws ParseException {
		LocalDate testPaymentDate = LocalDate.parse("2019-12-05");				
		Payment testPayment = new CreditPayment(testPaymentDate);
		OfferTerms originalTerms = new OfferTerms(0, null, testPayment);
		testOffer = new SellOffer(null, originalTerms, null, 0);
		testOffer.setId(1);
		
		LocalDate bidPaymentDate = LocalDate.parse("2019-12-07");	
		Payment bidPayment = new LetterOfCreditPayment(bidPaymentDate);
		OfferTerms bidTerms = new OfferTerms(0, null, bidPayment);
		Bid testBid = new Bid(bidTerms, testOffer.getId());
		testOffer.acceptBid(testBid);
		
		assertEquals("LC on 7 December 2019", testOffer.getPaymentType());
	}	
	
	/***** Private functions *****/
	
	private void assertListedFees(double price, double quantity, double expectedFees) {
		OfferTerms testTerms = new OfferTerms(price, null, null);
		testOffer = new SellOffer(null, testTerms, futureDate, quantity);
		assertEquals(expectedFees, testOffer.getFees(), 0.0d);
	}
	
	private void assertAcceptedFees(double price, double quantity, double fees) {
		OfferTerms testTerms = new OfferTerms(price + (price * 0.10), null, null);
		testOffer = new SellOffer(null, testTerms, futureDate, quantity);
		testOffer.setId(1);
		OfferTerms testBidTerms = new OfferTerms(price, null, null);
		testOffer.acceptBid(new Bid(testBidTerms, testOffer.getId()));		
		assertEquals(fees, testOffer.getFees(), 0.0d);
	}
	
}
