package com.mercury.it.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.mercury.model.Offer;
import com.mercury.model.Product;
import com.mercury.model.SellOffer;
import com.mercury.model.Terms;
import com.mercury.ut.gateways.jdbc.ProductGatewayJDBCTest;
import com.mercury.utilities.RandomUtility;
import com.mercury.model.BuyOffer;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.OffersGateway;

public class mockOffersGateway extends Gateway implements OffersGateway {
	
	private mockProductGateway productGateway;
	private mockTermsGateway termsGateway;

	public mockOffersGateway(String config) {
		super(config);
		termsGateway = new mockTermsGateway(config);	
		productGateway = new mockProductGateway(config);
	}

	@Override
	public ArrayList<Offer> getAllOffers() {
		ArrayList<Offer> mockOffers = new ArrayList<>();
		for (int i = 0; i < RandomUtility.getRandomInt(1, 50); i++){
			mockOffers.add(getRandomOffer());
		}
		return mockOffers;
	}

	@Override
	public void loadConfig(String config) {	}

	@Override
	public void initializeDatabase() {}

	@Override
	public boolean isInitialized() {
		return false;
	}

	@Override
	public long saveOffer(Offer offerToSave) {
		return 0;
	}

	public Offer getRandomOffer(){
		return createRandomOffer(RandomUtility.getRandomInt(1, 2), productGateway.getRandomProduct(), termsGateway.getRandomTerms(), RandomUtility.getRandomDateTime(), RandomUtility.getRandomDouble(1.00, 1000.00), RandomUtility.getRandomLong(1, 50));
	}

	public Offer getRandomOffer(Terms listedTerms){
		return createRandomOffer(RandomUtility.getRandomInt(1, 2), productGateway.getRandomProduct(), listedTerms, RandomUtility.getRandomDateTime(), RandomUtility.getRandomDouble(1.00, 1000.00), RandomUtility.getRandomLong(1, 50));
	}

	private Offer createRandomOffer(int OfferType, Product product, Terms listedTerms,  LocalDateTime dueDate, double Quantity, long ownerId ){
		Offer returnOffer = null;
		switch (OfferType){
			case 1:
				returnOffer = new BuyOffer(product, listedTerms, dueDate, Quantity, ownerId);
			case 2:
				returnOffer =  new SellOffer(product, listedTerms, dueDate, Quantity, ownerId);
		} 
		returnOffer.setId(RandomUtility.getRandomLong(1, 5000));
		return returnOffer;
	}
	
}
