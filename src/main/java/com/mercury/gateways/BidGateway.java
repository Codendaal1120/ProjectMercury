package com.mercury.gateways;

import com.mercury.model.Bid;

public interface BidGateway {
	public long saveBid(Bid bidToSave);
	public Bid getBidById(long BidId);	
}
