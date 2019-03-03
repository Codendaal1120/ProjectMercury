package com.mercury.gateways.jdbc;

import com.mercury.model.OfferTerms;
import com.mercury.utilities.DateUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mercury.gateways.OfferTermsGateway;
import com.mercury.gateways.Queries;

public class OfferTermsGatewayJDBC extends jdbcGateway implements OfferTermsGateway  {

	public OfferTermsGatewayJDBC(String config) {
		super(config);
	}

	@Override
	public long saveOfferTerms(OfferTerms terms) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.OfferTerms.saveOfferTerms, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, terms.getOfferId());
			preparedStatement.setDouble(2, terms.getTotalPrice());
			preparedStatement.setInt(3, terms.getDelivery().getDeliveryType());
			preparedStatement.setString(4, terms.getDelivery().getAddress());
			preparedStatement.setString(5, DateUtility.DateToString( terms.getDelivery().getDeliveryDate() , DateUtility.sqlDateFormat));
			preparedStatement.setInt(6, terms.getPayment().getPaymentMethod());
			preparedStatement.setString(7, DateUtility.DateToString( terms.getPayment().getPaymentDueDate() , DateUtility.sqlDateFormat));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			else{
				return -1;
			}
		} catch (Exception e) {
			logger.error("Unable to save delivery details (" + terms.toJsonString() + ") [" + e.getMessage() + "]");
			return -1;
		} finally {
			closeStatement(preparedStatement);
		}
	}

	
	@Override
	public OfferTerms getOfferTermsById(long offerTermsId) {
		/*
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.DeliveryDetails.getDeliveryDetailsById);
			preparedStatement.setLong(1, deliveryDetailsId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return createDeliveryDetailsFromResultSet(resultSet);
		} catch (Exception e) {
			logger.error("Unable to get delivery details with id '" + deliveryDetailsId + "' [" + e.getMessage() + "]");
			return null;
		} finally {
			closeStatement(preparedStatement);
		}
		*/
		return null;
	}

	@Override
	public void deleteOfferTerms(long termId) {

	}


}
