package com.mercury.gateways;

import com.mercury.model.Terms;

public interface TermsGateway {	
	public static final class PaymentType{
		public static final int CASH = 1;
		public static final int LETTER_OF_CREDIT = 2;
		public static final int CREDIT = 3;	
	}
	public long saveTerms(Terms terms);
	public Terms getTermsById(long termsId);	
}
