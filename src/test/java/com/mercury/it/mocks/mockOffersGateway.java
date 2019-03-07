package com.mercury.it.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.mercury.model.Offer;
import com.mercury.model.Product;
import com.mercury.model.SellOffer;
import com.mercury.model.Terms;
import com.mercury.utilities.RandomUtility;
import com.mercury.model.BuyOffer;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.OffersGateway;

public class mockOffersGateway extends Gateway implements OffersGateway {
	
	private Product[] products = { new Product(1, "50 PPM Diesel/Gas oil (Premium)"), new Product(2, "500 PPM Diesel/Gas oil (HSE)"), new Product(3, "HFO"), new Product(4, "95  RON MOGAS/Petrolium") };
	private mockTermsGateway termsGateway;

	public mockOffersGateway(String config) {
		super(config);
		termsGateway = new mockTermsGateway(config);	
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
		return createRandomOffer(RandomUtility.getRandomInt(1, 2), getRandomProduct(), termsGateway.getRandomTerms(), RandomUtility.getRandomDateTime(), RandomUtility.getRandomDouble(1.00, 1000.00), RandomUtility.getRandomLong(1, 50));
	}

	public Offer getRandomOffer(Terms listedTerms){
		return createRandomOffer(RandomUtility.getRandomInt(1, 2), getRandomProduct(), listedTerms, RandomUtility.getRandomDateTime(), RandomUtility.getRandomDouble(1.00, 1000.00), RandomUtility.getRandomLong(1, 50));
	}

	public Product getRandomProduct(){
		return products[RandomUtility.getRandomInt(0, products.length-1)];
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
