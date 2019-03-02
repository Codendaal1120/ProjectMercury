package com.mercury.gateways.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mercury.model.DeliveryDetails;
import com.mercury.gateways.DeliveryDetailsGateway;
import com.mercury.gateways.Queries;
import com.mercury.utilities.DateUtility;

public class DeliveryDetailsGatewayJDBC extends jdbcGateway implements DeliveryDetailsGateway {

	public DeliveryDetailsGatewayJDBC(String config) {
		super(config);
	}

	@Override
	public long saveDeliveryDetails(DeliveryDetails delivery) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.DeliveryDetails.saveDeliveryDetails,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, delivery.getDeliveryType());
			preparedStatement.setString(2, delivery.getAddress());
			preparedStatement.setString(3, DateUtility.DateToString(delivery.getDeliveryDate(), DateUtility.sqlDateFormat));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (Exception e) {
			logger.error("Unable to save delivery details (" + delivery.toJsonString() + ") [" + e.getMessage() + "]");
		} finally {
			closeStatement(preparedStatement);
		}
		return 0;
	}

	@Override
	public DeliveryDetails getDeliveryDetailsById(long deliveryDetailsId) {
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
	}

	private DeliveryDetails createDeliveryDetailsFromResultSet(ResultSet resultSet) throws SQLException {
		DeliveryDetails deliveryDetails = new DeliveryDetails( DateUtility.StringToDate(resultSet.getString( "delivery_date" ), DateUtility.sqlDateFormat ), resultSet.getString( "address" ), resultSet.getInt( "delivery_type" ) );
		deliveryDetails.id = resultSet.getLong( "id" );
		return deliveryDetails;
	}

}
