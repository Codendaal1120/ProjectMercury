package com.mercury.ut.model;

import static org.junit.Assert.fail;
import com.mercury.model.Bid;
import org.junit.Before;
import org.junit.Test;

public class BidTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testNullTermsShouldThowException() {
		try{
			new Bid(null, 0, 0);
			fail("Expected exception not thrown");
		}
		catch (NullPointerException e){
		}
	}
	
}
