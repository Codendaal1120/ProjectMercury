package com.mercury.it.mocks;

import com.mercury.gateways.BidGateway;
import com.mercury.gateways.Gateway;
import com.mercury.model.Bid;
import com.mercury.model.Terms;
import com.mercury.utilities.RandomUtility;

public class mockBidGateway extends Gateway implements BidGateway {

	private mockTermsGateway termsGateway;

	public mockBidGateway(String config){
		super(config);
		termsGateway = new mockTermsGateway(config);
	}

	@Override
	public void loadConfig(String config) {}

	@Override
	public void initializeDatabase() {}

	@Override
	public long saveBid(Bid bidToSave) {		
		return 0;
	}

	@Override
	public Bid getBidById(long BidId) {
		Bid newBid = getRandomBid();
		newBid.setId(BidId);
		return newBid;
	}

	public Bid getRandomBid(){
		return createBid(termsGateway.getRandomTerms(), RandomUtility.getRandomLong(1, 10), RandomUtility.getRandomLong(1, 10));
	}

	public Bid getRandomBid(Terms bidTerms){
		return createBid(bidTerms, RandomUtility.getRandomLong(1, 10), RandomUtility.getRandomLong(1, 10));
	}

	private Bid createBid(Terms bidTerm, long offerId, long ownerId){
		Bid newBid = new Bid(bidTerm, offerId, ownerId);
		newBid.setId(RandomUtility.getRandomLong(1, 50000));
		return newBid;
	}
}