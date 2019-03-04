package com.mercury.gateways.jdbc;

import com.mercury.model.CashPayment;
import com.mercury.model.CreditPayment;
import com.mercury.model.DeliveryDetails;
import com.mercury.model.LetterOfCreditPayment;
import com.mercury.model.PaymentDetails;
import com.mercury.model.Terms;
import com.mercury.utilities.DateUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

import com.mercury.gateways.TermsGateway;
import com.mercury.gateways.Queries;

public class TermsGatewayJDBC extends jdbcGateway implements TermsGateway {

	public TermsGatewayJDBC(String config) {
		super(config);
	}

	@Override
	public long saveTerms(Terms terms) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.Terms.saveTerms, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setDouble(1, terms.getTotalPrice());
			preparedStatement.setInt(2, terms.getDelivery().getDeliveryType());
			preparedStatement.setString(3, terms.getDelivery().getAddress());
			preparedStatement.setString(4,
					DateUtility.DateToString(terms.getDelivery().getDeliveryDate(), DateUtility.sqlDateFormat));
			preparedStatement.setInt(5, terms.getPayment().getPaymentMethod());
			preparedStatement.setString(6,
					DateUtility.DateToString(terms.getPayment().getPaymentDueDate(), DateUtility.sqlDateFormat));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error("Unable to save terms (" + terms.toJsonString() + ") [" + e.getMessage() + "]");
			return -1;
		} finally {
			closeStatement(preparedStatement);
		}
	}

	@Override
	public Terms getTermsById(long termsId) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.Terms.getTermsById);
			preparedStatement.setLong(1, termsId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return createTermsFromResultSet(resultSet);
		} catch (Exception e) {
			logger.error("Unable to get terms with id '" + termsId + "' [" + e.getMessage() + "]");
			return null;
		} finally {
			closeStatement(preparedStatement);
		}
	}

	private Terms createTermsFromResultSet(ResultSet resultSet) throws SQLException {
		DeliveryDetails delivery = new DeliveryDetails( LocalDate.parse( resultSet.getString( "delivery_date" ) ) , resultSet.getString( "delivery_address" ), resultSet.getInt( "delivery_type" ));
		PaymentDetails payment = null;
		switch (resultSet.getInt( "payment_type" )){
			case PaymentDetails.PaymentType.CASH :			
				payment = new CashPayment( LocalDate.parse( resultSet.getString( "payment_due_date" ) ) );
				break;
			case PaymentDetails.PaymentType.CREDIT :
				payment = new CreditPayment( LocalDate.parse( resultSet.getString( "payment_due_date" ) ) );
				break;
			case PaymentDetails.PaymentType.LETTER_OF_CREDIT :
				payment = new LetterOfCreditPayment( LocalDate.parse( resultSet.getString( "payment_due_date" ) ) );
				break;
		}
		Terms terms = new Terms(resultSet.getDouble( "total_price" ), delivery, payment);
		terms.setId( resultSet.getLong( "id" ) );
		return terms;
	}

}
