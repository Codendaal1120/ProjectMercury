package com.mercury.gateways;

import com.mercury.model.OfferTerms;

public interface OfferTermsGateway {
	
	public long saveOfferTerms(OfferTerms terms);
	public void deleteOfferTerms(long termId);
	public OfferTerms getOfferTermsById(long offerTermsId);
	
}
