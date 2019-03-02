package com.mercury.gateways;

import com.mercury.model.DeliveryDetails;

public interface DeliveryDetailsGateway {

	public long saveDeliveryDetails(DeliveryDetails delivery);
	public DeliveryDetails getDeliveryDetailsById(long deliveryDetailsId);
	
}
