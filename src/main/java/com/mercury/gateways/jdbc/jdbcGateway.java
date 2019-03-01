package com.mercury.gateways.jdbc;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import com.mercury.gateways.Gateway;

public class jdbcGateway extends Gateway {

	protected static final Logger logger = LogManager.getLogger(jdbcGateway.class.getName());
	private String dbUsername;
	private String dbPassword;
	private String dbServer;
	
	/***** Functions *****/
	
	public jdbcGateway(String config) {
		super(config);
		logger.debug("test");
		logger.info("test");
	}
	
	/***** Override Functions *****/

	@Override
	public void loadConfig(String config) {		
		Document document;		
		
		try {
			document = getXmlDocument(getInputStream(config));
	        dbUsername = document.getElementsByTagName("username").item(0).getTextContent();
	        dbPassword = document.getElementsByTagName("password").item(0).getTextContent();
	        dbServer = document.getElementsByTagName("server").item(0).getTextContent();
	        configLoaded = true;	        
        } catch (Exception ex) {
        	logger.error("Unable to get values from config file [" + ex.getMessage() + "]");
            //ex.printStackTrace();
            configLoaded = false;
        }		
	}

	@Override
	public void initializeDatabase() {	
		Connection connection = getConnection();
		if (getConnection() == null) {
			databaseInitialized = false;					
		}
		else {
			closeConnection(connection);
			databaseInitialized = true;
		}
	}
	
	/***** Private Functions *****/
	
	private InputStream getInputStream(String file) {		
		final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL configURL = classloader.getResource(file);
		if (configURL != null) {			
			return classloader.getResourceAsStream(file);
		}
		else {
			logger.error("Config file not found (" + file + ")");
			return null;
		}		
	}
	
	private Document getXmlDocument(InputStream inputStream) {		
		Document document;		
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
			document.getDocumentElement().normalize();
			return document;
		} catch (Exception e) {
			logger.error("Unable to parse XML config file [" + e.getMessage() + "]");
            //e.printStackTrace();
            configLoaded = false;
            return null;
		} 
	}	

	protected Connection getConnection() {
		
		try {
			DriverManager.setLoginTimeout(10);
			return DriverManager.getConnection(dbServer, dbUsername, dbPassword);
		} catch (Exception e) {
			logger.error("Unable to get connection [" + e.getMessage() + "]");
			return null;
		}
	}
	
	protected void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error("Unable to close connection [" + e.getMessage() + "]");
		}
	}
	
	protected void closeStatement(Statement statement){
		try
        {
            if (statement != null)
            {
            	statement.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            logger.error("Unable to close statement [" + e.getMessage() + "]");
        }
	}
}
