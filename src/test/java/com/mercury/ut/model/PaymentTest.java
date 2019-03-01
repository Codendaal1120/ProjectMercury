package com.mercury.ut.model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import com.mercury.model.CashPayment;
import com.mercury.model.CreditPayment;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.Payment;

import org.junit.Before;
import org.junit.Test;

public class PaymentTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testPaymentCashType() {
		Payment payment = new CashPayment(LocalDate.parse("2019-01-01"));
		assertEquals("Cash payment on 1 January 2019", payment.getPaymentType());
	}
	
	@Test
	public void testPaymentCreditType() {
		Payment payment = new CreditPayment(LocalDate.parse("2019-01-20"));
		assertEquals("Credit untill 20 January 2019", payment.getPaymentType());
	}
	
	@Test
	public void testPaymentLetterOfCreditType() {
		Payment payment = new LetterOfCreditPayment(LocalDate.parse("2019-02-01"));
		assertEquals("LC on 1 February 2019", payment.getPaymentType());
	}
	
}
