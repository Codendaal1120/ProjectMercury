package com.mercury.gateways;

import java.util.ArrayList;
import com.mercury.model.Product;

public interface ProductGateway {		
	public ArrayList<Product> getAllProducts();		
	public void loadConfig(String configFile);	
}
