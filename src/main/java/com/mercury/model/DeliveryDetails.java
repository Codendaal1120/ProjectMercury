package com.mercury.model;

import java.time.LocalDate;
import com.mercury.utilities.JsonUtility;

public class DeliveryDetails {
	
	public static final class DeliveryType{
		public static final int DELIVERY = 1;
		public static final int PICKUP = 2;		
	}	
	
	public long id;

	private String address;
	private LocalDate deliveryDate;
	private int deliveryMethod;
	
	/***** Functions *****/
	
	public DeliveryDetails(LocalDate deliveryDate, String address, int deliveryType) {
		this.address = address;
		this.deliveryDate = deliveryDate;
		this.deliveryMethod = deliveryType;
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
		return deliveryMethod;
	}
	
}
