package com.mercury.ut.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.mercury.model.Offer;
import com.mercury.model.Product;
import com.mercury.model.SellOffer;
import com.mercury.gateways.Gateway;
import com.mercury.gateways.OffersGateway;

public class mockOffersGateway extends Gateway implements OffersGateway {
	
	public mockOffersGateway(String config) {
		super(config);
	}

	@Override
	public ArrayList<Offer> getAllOffers() {
		ArrayList<Offer> mockOffers = new ArrayList<>();
		SellOffer sellOffer = new SellOffer(new Product(1, "TestProduct1"), null, LocalDateTime.now(), 200.00);
		mockOffers.add(sellOffer);
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
	
}