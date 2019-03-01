package com.mercury.gateways.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mercury.model.DeliveryDetails;
import com.mercury.gateways.DeliveryDetailsGateway;
import com.mercury.gateways.Queries;
import com.mercury.utilities.DateUtility;

public class DeliveryDetailsGatewayJDBC extends jdbcGateway implements DeliveryDetailsGateway{

	public DeliveryDetailsGatewayJDBC(String config) {
		super(config);
	}

	@Override
	public long saveDeliveryDetails(DeliveryDetails delivery) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.DeliveryDetails.saveDeliveryDetails, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, delivery.getDeliveryType());
			preparedStatement.setString(2, delivery.getAddress());
			preparedStatement.setString(3, DateUtility.DateToString(delivery.getDeliveryDate(), "YYYY-MM-dd"));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}
		catch(Exception e) {
			logger.error("Unable to save delivery details (" + delivery.toJsonString() + ") [" + e.getMessage() + "]");
		}
		finally {
			closeStatement(preparedStatement);
		}
		return 0;
	}

}
