package com.mercury.it.gateways.jdbc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercury.application.model.DeliveryDetails;
import com.mercury.gateways.DeliveryDetailsGateway;
import com.mercury.gateways.jdbc.DeliveryDetailsGatewayJDBC;

public class DeliveryDetailsGatewayJDBCTest {
	
	private DeliveryDetailsGateway deliveryDetailsGateway;
	
	@BeforeEach
	public void setUp() {
		deliveryDetailsGateway = new DeliveryDetailsGatewayJDBC("jdbc.xml");
	}
	
	@Test
	public void testCanSaveDeliveryDetails() {
		DeliveryDetails delivery = new DeliveryDetails(Instant.now(), "Some address", DeliveryDetails.DeliveryType.DELIVERY);
		assertTrue(deliveryDetailsGateway.saveDeliveryDetails(delivery) > 0);
	}
	
}
