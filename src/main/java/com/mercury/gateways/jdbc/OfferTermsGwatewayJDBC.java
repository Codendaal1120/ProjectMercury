package com.mercury.gateways.jdbc;

import com.mercury.model.OfferTerms;
import com.mercury.gateways.OfferTermsGateway;

public class OfferTermsGwatewayJDBC extends jdbcGateway implements OfferTermsGateway  {

	public OfferTermsGwatewayJDBC(String config) {
		super(config);
	}

	@Override
	public long saveOfferTerms(OfferTerms terms) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteOfferTerms(long termId) {
		// TODO Auto-generated method stub
		
	}

}
