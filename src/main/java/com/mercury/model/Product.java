package com.mercury.model;

public class Product {
	
	private long id;
	private String name;	
	
	public Product(long id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}	
	
	public String getName() {
		return this.name;
	}
	
}
