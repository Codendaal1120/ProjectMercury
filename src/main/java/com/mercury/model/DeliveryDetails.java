package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.JsonUtility;

public class DeliveryDetails {
	
	public static final class DeliveryType{
		public static final int DELIVERY = 1;
		public static final int PICKUP = 2;		
	}	
	
	private String address;
	private LocalDate deliveryDate;
	private int deliveryType;
	
	/***** Functions *****/
	
	public DeliveryDetails(LocalDate deliveryDate, String address, int deliveryType) {
		this.address = address;
		this.deliveryDate = deliveryDate;
		this.deliveryType = deliveryType;
	}
	
	public String toJsonString() {
		return JsonUtility.objectToJson(this);		
	}
	
	/***** get functions *****/
	
	public String getAddress() {
		return address;
	}
	
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	
	public int getDeliveryType() {
		return deliveryType;
	}
	
}
