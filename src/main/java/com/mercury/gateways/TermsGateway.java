package com.mercury.gateways;

import com.mercury.model.Terms;

public interface TermsGateway {
	
	public long saveTerms(Terms terms);
	public Terms getTermsById(long termsId);
	
}
