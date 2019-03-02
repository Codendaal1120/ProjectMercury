package com.mercury.it.gateways.jdbc;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import com.mercury.model.DeliveryDetails;
import com.mercury.gateways.DeliveryDetailsGateway;
import com.mercury.gateways.jdbc.DeliveryDetailsGatewayJDBC;

public class DeliveryDetailsGatewayJDBCTest {
	
	private DeliveryDetailsGateway deliveryDetailsGateway;
	
	@Before
	public void setUp() {
		deliveryDetailsGateway = new DeliveryDetailsGatewayJDBC("jdbc.xml");
	}
	
	@Test
	public void testCanSaveDeliveryDetails() {
		DeliveryDetails delivery = new DeliveryDetails(LocalDate.now(), "Some address", DeliveryDetails.DeliveryType.DELIVERY);
		assertTrue(deliveryDetailsGateway.saveDeliveryDetails(delivery) > 0);
	}
	
}
