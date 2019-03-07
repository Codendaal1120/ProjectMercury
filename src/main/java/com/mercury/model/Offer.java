package com.mercury.model;

import java.time.LocalDateTime;
import com.mercury.utilities.DateUtility;
import com.mercury.utilities.JsonUtility;
import com.mercury.utilities.NumberUtility;

public abstract class Offer {
	
	public static final class OfferStatus{
		public static final int NEW = 0;
		public static final int OPEN = 1;
		public static final int EXPIRED = 2;
		public static final int CANCELLED = 3;
		public static final int CLOSED = 4;	
	}	
	
	private long ownerId;	
	private long id;
	private double quantity;
	private int Status = OfferStatus.NEW;	
	private LocalDateTime dueDate;	
	private Terms listedTerms;
	private Bid acceptedBid;
	private Product product;
	private String unit = "\u2113";
	
	/***** Functions *****/

	public Offer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId, Bid accepteBid) {
		this(product, listedTerms, dueDate, quantity, ownerId);
		this.acceptedBid = accepteBid;		
	}
	
	public Offer(Product product, Terms listedTerms, LocalDateTime dueDate, double quantity, long ownerId) {
		if (product == null){
			throw new NullPointerException("Product cannot be null");
		}
		else if (listedTerms == null){
			throw new NullPointerException("ListedTerms cannot be null");
		}
		else{
			this.ownerId = ownerId;
			this.product = product;
			this.listedTerms = listedTerms;
			this.dueDate = dueDate;
			this.quantity = quantity;
		}		
	}
	
	public void acceptBid(Bid acceptedBid) {
		//TODO: test terms and listed cannot be equal
		if (acceptedBid == null){
			throw new NullPointerException("Cannot accept a null Bid");
		}
		else{
			this.acceptedBid = acceptedBid;
			this.Status = OfferStatus.CLOSED;
		}	
	}

	public String toJsonString(){
		return JsonUtility.objectToJson(this);
	}
	
	/***** Boolean functions *****/
	
	public boolean isExpired() {		
		return dueDate.isBefore(LocalDateTime.now());
	}	
	
	/***** get functions *****/
	
	public long getId() {
		return id;
	}

	public long getOwnerId(){
		return ownerId;
	}
	
	public double getUnitPrice() {
		return listedTerms.getTotalPrice() / quantity;
	}
	
	public double getTotalPrice() {
		if (Status == OfferStatus.CLOSED) {
			return acceptedBid.getTotalPrice();
		}
		else {
			return listedTerms.getTotalPrice();
		}
	}
	
	public double getQuantity() {
		return this.quantity;
	}
		
	public double getFees() {
		//For the sake of this demo, the fee structure has been unnecessarily complicated  
		if (getQuantity() >= 50000.00) {
			return getAbove50kFee();
		}
		else if (getQuantity() >= 25000.00) {
			return getAbove25kFee();
		}
		else if (getQuantity() >= 10000.00) {
			return getAbove10kFee();
		}
		else if (getQuantity() < 300){
			return 0;
		}
		else {
			return getStandardFee();
		}
	}

	public int getStatus() {
		if (isExpired()) {
			return OfferStatus.EXPIRED;
		}
		else if (id == 0) {
			return OfferStatus.NEW;
		}
		else {
			return Status;
		}
	}

	public LocalDateTime getDueDate(){
		return this.dueDate;
	}

	public String getTimeRemaining() {
		if (isExpired()) {
			return DateUtility.TimeDiffrenceHoursMinutes(dueDate, LocalDateTime.now());
		}
		else {
			return DateUtility.TimeDiffrenceHoursMinutes(LocalDateTime.now(), dueDate);
			
		}		
	}
	
	public String getPaymentType() {
		if (Status == OfferStatus.CLOSED) {
			return acceptedBid.getPaymentType();
		}
		else {
			return listedTerms.getPaymentMethodText();
		}
	}
	
	public DeliveryDetails getDelivery() {
		if (Status == OfferStatus.CLOSED) {
			return acceptedBid.getDelivery();
		}
		else {
			return listedTerms.getDelivery();
		}
	}
	
	public Terms getListedTerms(){
		return this.listedTerms;
	}

	public Bid getAcceptedBid(){
		return this.acceptedBid;
	}

	public Product getProduct(){
		return this.product;
	}
	
	/***** set functions *****/
	
	public void setStatus(int Status) {
		this.Status = Status;
	}
	
	public void setId(long id) {
		this.id = id;
		if (id > 0 && Status == OfferStatus.NEW) {
			setStatus(OfferStatus.OPEN);
		}
	}
	
	/***** private functions *****/

	private double getAbove10kFee() {
		return NumberUtility.Round(((getTotalPrice() * 0.3) * 0.0032) + ((getTotalPrice() * 0.7) * 0.0035), 2);
	}
	
	private double getAbove25kFee() {
		return NumberUtility.Round(((getTotalPrice() * 0.4) * 0.0027) + ((getTotalPrice() * 0.6) * 0.0035), 2);
	}
	
	private double getAbove50kFee() {
		return NumberUtility.Round(((getTotalPrice() * 0.5) * 0.002) + ((getTotalPrice() * 0.5) * 0.0035), 2);
	}
	
	private double getStandardFee() {
		return NumberUtility.Round(getTotalPrice() * 0.0035, 2);
	}
	
}
