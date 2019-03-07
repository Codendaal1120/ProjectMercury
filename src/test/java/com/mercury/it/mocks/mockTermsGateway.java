package com.mercury.it.mocks;

import java.time.LocalDate;

import com.mercury.gateways.Gateway;
import com.mercury.gateways.TermsGateway;
import com.mercury.model.CashPayment;
import com.mercury.model.CreditPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.PaymentDetails;
import com.mercury.model.Terms;
import com.mercury.utilities.RandomUtility;

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
		Terms newTerms = getRandomTerms();
		newTerms.setId(termsId);
		return newTerms;
	}

	@Override
	public void loadConfig(String config) {	}

	@Override
	public void initializeDatabase() {	}

	public Terms getRandomTerms(){
		return createRandomTerms(RandomUtility.getRandomDouble(100.00, 10000.000), getRandomDeliveryDetails(), getRandomPaymentDetails());
	}

	public Terms getRandomTerms(PaymentDetails payment){
		return createRandomTerms(RandomUtility.getRandomDouble(100.00, 10000.000), getRandomDeliveryDetails(), payment);
	}

	public Terms getRandomTerms(double totalPrice){
		return createRandomTerms(totalPrice, getRandomDeliveryDetails(), getRandomPaymentDetails());
	}

	public Terms getRandomTerms(DeliveryDetails delivery){
		return createRandomTerms(RandomUtility.getRandomDouble(100.00, 10000.000), delivery, getRandomPaymentDetails());
	}

	public PaymentDetails getRandomPaymentDetails(){
		switch (RandomUtility.getRandomInt(1, 3)){
			case 1:
				return new CashPayment(RandomUtility.getRandomDate());
			case 2:
				return new CreditPayment(RandomUtility.getRandomDate());
			case 3:
				return new LetterOfCreditPayment(RandomUtility.getRandomDate());
		}
		return null;
	}

	public DeliveryDetails getRandomDeliveryDetails(){
		return createRandomDeliveryDetails(RandomUtility.getRandomDate() , RandomUtility.getRandomString(RandomUtility.getRandomInt(5, 30)), DeliveryDetails.DeliveryType.DELIVERY);
	}

	public DeliveryDetails getRandomDeliveryDetails(String address){
		return createRandomDeliveryDetails(RandomUtility.getRandomDate() , address, DeliveryDetails.DeliveryType.DELIVERY);
	}

	public DeliveryDetails getRandomDeliveryDetails(LocalDate deliveryDate){
		return createRandomDeliveryDetails(deliveryDate, RandomUtility.getRandomString(RandomUtility.getRandomInt(5, 30)), DeliveryDetails.DeliveryType.DELIVERY);
	}

	public DeliveryDetails getRandomDeliveryDetails(int deliveryType){
		return createRandomDeliveryDetails(RandomUtility.getRandomDate() , RandomUtility.getRandomString(RandomUtility.getRandomInt(5, 30)), deliveryType);
	}

	private Terms createRandomTerms(double totalPrice, DeliveryDetails delivery, PaymentDetails payment){
		Terms returnTerms = new Terms(totalPrice, delivery, payment);
		returnTerms.setId(RandomUtility.getRandomLong(1, 1000000));
		return returnTerms;
	}

	private DeliveryDetails createRandomDeliveryDetails(LocalDate deliveryDate, String address, int deliveryType){
		return new DeliveryDetails(deliveryDate, address, deliveryType);
	}

}