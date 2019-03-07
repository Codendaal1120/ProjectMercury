package com.mercury.it.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import com.mercury.model.Bid;
import com.mercury.model.CreditPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.Offer;
import com.mercury.model.Terms;
import com.mercury.exceptions.ConflictException;
import com.mercury.it.mocks.mockBidGateway;
import com.mercury.it.mocks.mockOffersGateway;
import com.mercury.it.mocks.mockProductGateway;
import com.mercury.it.mocks.mockTermsGateway;
import com.mercury.utilities.RandomUtility;
import com.mercury.model.PaymentDetails;
import com.mercury.model.BuyOffer;
import org.junit.Before;
import org.junit.Test;

public class OfferTest {

	private LocalDateTime futureDate;
	private LocalDateTime pastDate;
	private mockTermsGateway termsGateway;
	private mockBidGateway bidGateway;
	private mockOffersGateway offerGateway;
	private mockProductGateway productGateway;
	private Offer testOffer;
	
	@Before
	public void setUp() {
		termsGateway = new mockTermsGateway("duymmy.confg");
		bidGateway = new mockBidGateway("dummy.config");
		offerGateway = new mockOffersGateway("dummy.config");
		productGateway = new mockProductGateway("dummy.config");
		futureDate = LocalDateTime.now().plus(10, ChronoUnit.DAYS);
		pastDate = LocalDateTime.now().plus(-10, ChronoUnit.DAYS);
		testOffer = new BuyOffer(productGateway.getRandomProduct(), termsGateway.getRandomTerms(), futureDate, RandomUtility.getRandomDouble(0, 1000), RandomUtility.getRandomLong(0, 10));
	}

	@Test
	public void testNullProductShouldThrowException() {		
		try{
			new BuyOffer(null, termsGateway.getRandomTerms(), futureDate, 0, 0);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
	}

	@Test
	public void testNullListedTermsShouldThrowException() {		
		try{
			new BuyOffer(productGateway.getRandomProduct(), null, futureDate, 0, 0);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
	}	
	
	@Test
	public void testNewOfferShouldHaveNewStatus() {		
		BuyOffer offer = new BuyOffer(productGateway.getRandomProduct(), termsGateway.getRandomTerms(), futureDate, RandomUtility.getRandomDouble(0, 1000), RandomUtility.getRandomLong(0, 10));
		assertEquals(Offer.OfferStatus.NEW, offer.getStatus());		
	}
	
	@Test
	public void testSetIdShouldChangeStatus() {		
		
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
	public void testAcceptBidTermsIsSameAsListedTermsShouldFail(){
		try{
			Terms testTerms = termsGateway.getRandomTerms();
			testOffer = offerGateway.getRandomOffer(testTerms);
			Bid testBid = bidGateway.getRandomBid(testTerms);
			testOffer.acceptBid(testBid);
			fail("Expected exception not thrown");
		}
		catch (ConflictException e){}
	}
	
	@Test
	public void testAcceptingBidShouldChangeStatus() {
		testOffer.setId(1);
		testOffer.acceptBid(bidGateway.getRandomBid());
		assertEquals(Offer.OfferStatus.CLOSED, testOffer.getStatus());		
	}
	
	@Test
	public void testOfferShouldBeExpired() {
		testOffer = new BuyOffer(productGateway.getRandomProduct(), termsGateway.getRandomTerms(), pastDate, 0, 0);
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
		Terms newTerms = termsGateway.getRandomTerms(760);
		Offer testOffer = new BuyOffer(productGateway.getRandomProduct(), newTerms, futureDate, 200.00, 0);
		assertEquals(3.8, testOffer.getUnitPrice(), 0.0d);		
	}

	@Test
	public void testAcceptingBidShouldChangeUnitPrice(){
		Terms newTerms = termsGateway.getRandomTerms(500);
		Terms bidTerms = termsGateway.getRandomTerms(760);
		Offer testOffer = new BuyOffer(productGateway.getRandomProduct(), newTerms, futureDate, 200.00, 0);
		testOffer.acceptBid(bidGateway.getRandomBid(bidTerms));
		assertEquals(3.8, testOffer.getUnitPrice(), 0.0d);	
	}
	
	@Test
	public void testOfferFees() {	
		assertListedFees(829917, 9999, 2904.71);
		assertListedFees(843600, 10000, 2876.68);
		assertListedFees(2066250, 25000, 6570.68);
		assertListedFees(4150000, 50000, 11412.50);
		assertListedFees(4150000, 299, 0);
		assertListedFees(1, 300, 0);
		assertListedFees(1, 1, 0);
		assertListedFees(2490000, 30000, 7918.20);
	}
	
	@Test
	public void testAcceptingBidShouldChangeFees() {
		assertAcceptedFees(829917, 9999, 2904.71);
		assertAcceptedFees(843600, 10000, 2876.68);		
		assertAcceptedFees(2066250, 25000, 6570.68);
		assertAcceptedFees(4150000, 50000, 11412.50);
		assertAcceptedFees(4150000, 299, 0);
		assertAcceptedFees(1, 300, 0);
		assertAcceptedFees(1, 1, 0);
		assertAcceptedFees(2490000, 30000, 7918.20);
	}	
	
	@Test
	public void testAcceptingBidShouldChangePrice() {
		testOffer = new BuyOffer(productGateway.getRandomProduct(), termsGateway.getRandomTerms() , futureDate, 0, 0);
		testOffer.setId(1);		
		Terms bidTerms = termsGateway.getRandomTerms(50000.0);
		Bid testBid = new Bid(bidTerms, testOffer.getId(), 1);
		testOffer.acceptBid(testBid);		
		assertEquals(50000, testOffer.getTotalPrice(), 0.0d);
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryAddress(){
		String testDeliveryAddress = "testAcceptingBidShouldChangeDeliveryAddress";		
		DeliveryDetails bidDelivery = termsGateway.getRandomDeliveryDetails(testDeliveryAddress);
		testOffer = offerGateway.getRandomOffer();
		Terms bidTerms = termsGateway.getRandomTerms(bidDelivery);
		Bid testBid = bidGateway.getRandomBid(bidTerms);
		testOffer.acceptBid(testBid);		
		assertEquals(testDeliveryAddress, testOffer.getDelivery().getAddress());
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryDate() throws ParseException {
		LocalDate testDeliveryDate = LocalDate.parse("2019-12-05");		
		DeliveryDetails bidDelivery = termsGateway.getRandomDeliveryDetails(testDeliveryDate);
		testOffer = offerGateway.getRandomOffer();
		Terms bidTerms = termsGateway.getRandomTerms(bidDelivery);
		Bid testBid = bidGateway.getRandomBid(bidTerms);
		testOffer.acceptBid(testBid);		
		assertEquals(testDeliveryDate, testOffer.getDelivery().getDeliveryDate());
	}	
	
	@Test
	public void testAcceptingBidShouldChangeDeliveryType() {
		int testDeliveryType = DeliveryDetails.DeliveryType.PICKUP;	
		DeliveryDetails bidDelivery = termsGateway.getRandomDeliveryDetails(testDeliveryType);
		testOffer = offerGateway.getRandomOffer();
		Terms bidTerms = termsGateway.getRandomTerms(bidDelivery);
		Bid testBid = bidGateway.getRandomBid(bidTerms);
		testOffer.acceptBid(testBid);		
		assertEquals(testDeliveryType, testOffer.getDelivery().getDeliveryType());
	}	
	
	@Test
	public void testAcceptingBidShouldChangePaymentType() throws ParseException {
		LocalDate bidDate = LocalDate.parse("2019-12-07");
		PaymentDetails testPayment = new CreditPayment(RandomUtility.getRandomDate());
		Terms originalTerms = termsGateway.getRandomTerms(testPayment);
		testOffer = offerGateway.getRandomOffer(originalTerms);
		PaymentDetails bidPayment = new LetterOfCreditPayment(bidDate);
		Bid testBid = bidGateway.getRandomBid(termsGateway.getRandomTerms(bidPayment));
		testOffer.acceptBid(testBid);
		assertEquals("LC on 7 December 2019", testOffer.getPaymentType());
	}	
	
	/***** Private functions *****/
	
	private void assertListedFees(double price, double quantity, double expectedFees) {
		Terms originalTerms = termsGateway.getRandomTerms(price);
		testOffer = new BuyOffer(productGateway.getRandomProduct(), originalTerms, futureDate, quantity, RandomUtility.getRandomLong(1, 50));
		assertEquals(expectedFees, testOffer.getFees(), 0.0d);
	}
	
	private void assertAcceptedFees(double price, double quantity, double expectedFees) {		
		Terms originalTerms = termsGateway.getRandomTerms(price + (price * 0.10));
		testOffer = new BuyOffer(productGateway.getRandomProduct(), originalTerms, futureDate, quantity, RandomUtility.getRandomLong(1, 50));
		Terms bidTerms = termsGateway.getRandomTerms(price);
		testOffer.acceptBid(bidGateway.getRandomBid(bidTerms));
		assertEquals(expectedFees, testOffer.getFees(), 0.0d);
	}
	
}
