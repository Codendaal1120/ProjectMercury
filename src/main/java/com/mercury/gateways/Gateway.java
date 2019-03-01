package com.mercury.gateways;

public abstract class Gateway {
	
	protected boolean configLoaded = false;
	protected boolean databaseInitialized = false;
	
	public Gateway(String config) {
		loadConfig(config);
	}
	
	public abstract void loadConfig(String config);
	
	public abstract void initializeDatabase();
	
	public boolean isInitialized() {
		return databaseInitialized;
	}
	
	public boolean isConfigLoaded() {
		return configLoaded;
	}
	
}
