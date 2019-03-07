package com.mercury.it.model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import com.mercury.model.CashPayment;
import com.mercury.model.CreditPayment;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.PaymentDetails;

import org.junit.Before;
import org.junit.Test;

public class PaymentTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testPaymentCashType() {
		PaymentDetails payment = new CashPayment(LocalDate.parse("2019-01-01"));
		assertEquals("Cash payment on 1 January 2019", payment.getPaymentMethodText());
	}
	
	@Test
	public void testPaymentCreditType() {
		PaymentDetails payment = new CreditPayment(LocalDate.parse("2019-01-20"));
		assertEquals("Credit untill 20 January 2019", payment.getPaymentMethodText());
	}
	
	@Test
	public void testPaymentLetterOfCreditType() {
		PaymentDetails payment = new LetterOfCreditPayment(LocalDate.parse("2019-02-01"));
		assertEquals("LC on 1 February 2019", payment.getPaymentMethodText());
	}
	
}
