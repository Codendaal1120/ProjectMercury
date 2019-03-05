package com.mercury.gateways;

import java.util.ArrayList;

import com.mercury.model.Offer;

public interface OffersGateway {
	public static final class OfferType{
		public static final int BUY = 1;
		public static final int SELL = 2;
	}
	public long saveOffer(Offer offerToSave);
	public ArrayList<Offer> getAllOffers();	
}
