package com.mercury.gateways;

import java.util.ArrayList;
import com.mercury.model.Bid;

public interface BidGateway {
	public long saveBid(Bid bidToSave);
	public ArrayList<Bid> getBidById(long BidId);	
}
