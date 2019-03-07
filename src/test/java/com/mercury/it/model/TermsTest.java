package com.mercury.it.model;

import static org.junit.Assert.fail;
import java.time.LocalDate;
import com.mercury.model.CashPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.Terms;
import org.junit.Before;
import org.junit.Test;

public class TermsTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testNullDeliveryShouldThowException() {
		try{
			new Terms(0.00, null, new CashPayment(LocalDate.now()));
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
	}

	@Test
	public void testNullPaymentShouldThowException() {
		try{
			new Terms(0.00, new DeliveryDetails(LocalDate.now(), "some address", 1), null);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){}
	}
	
}
