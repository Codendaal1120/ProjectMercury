package com.mercury.ut.mocks;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.TermsGateway;
import com.mercury.model.CashPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.PaymentDetails;
import com.mercury.model.Terms;

public class mockTermsGateway extends Gateway implements TermsGateway {

	public mockTermsGateway(String config) {
		super(config);
	}

	@Override
	public long saveTerms(Terms terms) {
		return 0;
	}

	@Override
	public Terms getTermsById(long termsId) {
		DeliveryDetails delivery = new DeliveryDetails(LocalDate.now().plus(10, ChronoUnit.DAYS) , "some address", DeliveryDetails.DeliveryType.DELIVERY);
		PaymentDetails payment = new CashPayment(LocalDate.now().plus(10, ChronoUnit.DAYS));
		Terms newTerms = new Terms(100.00, delivery, payment);
		newTerms.setId(termsId);
		return newTerms;
	}

	@Override
	public void loadConfig(String config) {	}

	@Override
	public void initializeDatabase() {	}
}