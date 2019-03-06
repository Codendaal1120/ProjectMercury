package com.mercury.ut.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import com.mercury.gateways.TermsGateway;
import com.mercury.model.Bid;
import com.mercury.model.CreditPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.Offer;
import com.mercury.model.Terms;
import com.mercury.ut.mocks.mockTermsGateway;
import com.mercury.model.PaymentDetails;
import com.mercury.model.Product;
import com.mercury.model.BuyOffer;
import org.junit.Before;
import org.junit.Test;

public class OfferTest {

	private LocalDateTime futureDate;
	private LocalDateTime pastDate;
	private TermsGateway termsGateway;
	private Offer testOffer;	
	private Product testProduct;
	private Terms testTerms;
	
	@Before
	public void setUp() {
		futureDate = LocalDateTime.now().plus(10, ChronoUnit.DAYS);
		pastDate = LocalDateTime.now().plus(-10, ChronoUnit.DAYS);
		testProduct = new Product(0, "Test product");
		termsGateway = new mockTermsGateway("duymmy.confg");
		testTerms = termsGateway.getTermsById(1);				
		testOffer = new BuyOffer(testProduct, testTerms, futureDate, 0, 0);
	}

	@Test
	public void testNullProductShouldThrowException() {		
		try{
			new BuyOffer(null, testTerms, futureDate, 0, 0);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
	}

	@Test
	public void testNullListedTermsShouldThrowException() {		
		try{
			new BuyOffer(testProduct, null, futureDate, 0, 0);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
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
	public void testAcceptingNullBidShouldThrowException(){
		try{
			testOffer.acceptBid(null);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}		
	}
	
	@Test
	public void testAcceptingBidShouldChangeStatus() {
		testOffer.setId(1);
		testOffer.acceptBid(new Bid(new Terms(0, null, null), testOffer.getId(), 0));
		assertEquals(Offer.OfferStatus.CLOSED, testOffer.getStatus());		
	}
	
	@Test
	public void testOfferShouldBeExpired() {
		testOffer = new BuyOffer(testProduct, testTerms, pastDate, 0, 0);
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
		Terms newTerms = new Terms(760, testTerms.getDelivery(), testTerms.getPayment());
		Offer testOffer = new BuyOffer(testProduct, newTerms, futureDate, 200.00, 0);
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
		Terms originalTerms = new Terms(10000, testTerms.getDelivery(), testTerms.getPayment());
		testOffer = new BuyOffer(testProduct, originalTerms, futureDate, 0, 0);
		testOffer.setId(1);		
		Terms bidTerms = new Terms(50000, testTerms.getDelivery(), testTerms.getPayment());
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertEquals(50000, testOffer.getTotalPrice(), 0.0d);
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryAddress(){
		String testDeliveryAddress = "testAcceptingBidShouldChangeDeliveryAddress";		
		DeliveryDetails bidDelivery = new DeliveryDetails(null, testDeliveryAddress, 0);	
		testOffer = new BuyOffer(testProduct, testTerms, null, 0, 0);
		testOffer.setId(1);				
		Terms bidTerms = new Terms(0, bidDelivery, testTerms.getPayment());
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertEquals(testDeliveryAddress, testOffer.getDelivery().getAddress());
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryDate() throws ParseException {
		LocalDate testDeliveryDate = LocalDate.parse("2019-12-05");		
		DeliveryDetails originalDelivery = new DeliveryDetails(LocalDate.parse("2019-01-12"), null, 0);
		Terms originalTerms = new Terms(0, originalDelivery, testTerms.getPayment());
		testOffer = new BuyOffer(testProduct, originalTerms, futureDate, 0, 0);
		testOffer.setId(1);		
		DeliveryDetails bidDelivery = new DeliveryDetails(testDeliveryDate, null, 0);
		Terms bidTerms = new Terms(0, bidDelivery, testTerms.getPayment());
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertTrue(testDeliveryDate.compareTo(testOffer.getDelivery().getDeliveryDate()) == 0);
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryType() {
		int testDeliveryType = DeliveryDetails.DeliveryType.PICKUP;		
		DeliveryDetails originalDelivery = new DeliveryDetails(null, null, DeliveryDetails.DeliveryType.DELIVERY);
		Terms originalTerms = new Terms(0, originalDelivery, testTerms.getPayment());
		testOffer = new BuyOffer(testProduct, originalTerms, futureDate, 0, 0);
		testOffer.setId(1);		
		DeliveryDetails bidDelivery = new DeliveryDetails(null, null, testDeliveryType);
		Terms bidTerms = new Terms(0, bidDelivery, testTerms.getPayment());
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertEquals(testDeliveryType, testOffer.getDelivery().getDeliveryType());
	}	
	
	@Test
	public void testAcceptingBidShouldChangePaymentType() throws ParseException {
		LocalDate testPaymentDate = LocalDate.parse("2019-12-05");				
		PaymentDetails testPayment = new CreditPayment(testPaymentDate);
		Terms originalTerms = new Terms(0, testTerms.getDelivery(), testPayment);
		testOffer = new BuyOffer(testProduct, originalTerms, null, 0, 0);
		testOffer.setId(1);		
		LocalDate bidPaymentDate = LocalDate.parse("2019-12-07");	
		PaymentDetails bidPayment = new LetterOfCreditPayment(bidPaymentDate);
		Terms bidTerms = new Terms(0, testTerms.getDelivery(), bidPayment);
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertEquals("LC on 7 December 2019", testOffer.getPaymentType());
	}	
	
	/***** Private functions *****/
	
	private void assertListedFees(double price, double quantity, double expectedFees) {
		Terms newTerms = new Terms(price, testTerms.getDelivery(), testTerms.getPayment());
		testOffer = new BuyOffer(testProduct, newTerms, futureDate, quantity, 0);
		assertEquals(expectedFees, testOffer.getFees(), 0.0d);
	}
	
	private void assertAcceptedFees(double price, double quantity, double fees) {
		Terms newTerms = new Terms(price + (price * 0.10), testTerms.getDelivery(), testTerms.getPayment());
		testOffer = new BuyOffer(testProduct, newTerms, futureDate, quantity, 0);
		testOffer.setId(1);
		Terms testBidTerms = new Terms(price, testTerms.getDelivery(), testTerms.getPayment());
		testOffer.acceptBid(new Bid(testBidTerms, testOffer.getId(), 0));		
		assertEquals(fees, testOffer.getFees(), 0.0d);
	}
	
}
