package com.mercury.gateways.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.mercury.model.Offer;
import com.mercury.gateways.OffersGateway;
import com.mercury.gateways.Queries;

public class OffersGatewayJDBC extends jdbcGateway implements OffersGateway  {

	public OffersGatewayJDBC(String config) {
		super(config);
	}

	@Override
	public ArrayList<Offer> getAllOffers() {
		ArrayList<Offer> offers = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(Queries.Offers.getAllOffers);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				//offers.add(new Offer();
			}
		}
		catch (Exception e) {
			logger.error("Unable to get offers [" + e.getMessage() + "]");
		}
		finally {
			closeStatement(preparedStatement);
		}
		return offers;
	}
	
	
}
