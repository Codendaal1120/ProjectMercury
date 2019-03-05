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
			preparedStatement.setString(4, DateUtility.DateToString(terms.getDelivery().getDeliveryDate(), DateUtility.sqlDateFormat));
			preparedStatement.setInt(5, getPaymentType(terms.getPayment()));
			preparedStatement.setString(6, DateUtility.DateToString(terms.getPayment().getPaymentDueDate(), DateUtility.sqlDateFormat));
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

	public Terms createTermsFromResultSetWithPrefix(ResultSet resultSet, String fieldPerfix) throws SQLException {
		DeliveryDetails delivery = new DeliveryDetails( LocalDate.parse( resultSet.getString( fieldPerfix + "delivery_date" ) ) , resultSet.getString( fieldPerfix + "delivery_address" ), resultSet.getInt( fieldPerfix + "delivery_type" ));
		PaymentDetails payment = createPaymentDetails(resultSet.getInt( fieldPerfix + "payment_type" ), resultSet.getString( fieldPerfix + "payment_due_date" ) );		
		Terms terms = new Terms(resultSet.getDouble( fieldPerfix + "total_price" ), delivery, payment);
		terms.setId( resultSet.getLong( fieldPerfix + "id" ) );
		return terms;
	}

	public Terms createTermsFromResultSet(ResultSet resultSet) throws SQLException {
		return createTermsFromResultSetWithPrefix(resultSet, "");
	}

	private int getPaymentType(PaymentDetails payment){
		if (payment instanceof CashPayment){
			return TermsGateway.PaymentType.CASH;
		}
		else if (payment instanceof CreditPayment){
			return TermsGateway.PaymentType.CREDIT;
		}
		else if (payment instanceof LetterOfCreditPayment){
			return TermsGateway.PaymentType.LETTER_OF_CREDIT;
		}
		else{
			return -1;
		}
	}

	private PaymentDetails createPaymentDetails(int PaymentType, String paymentDueDate){
		PaymentDetails payment = null;
		switch (PaymentType){
			case TermsGateway.PaymentType.CASH :			
				payment = new CashPayment( LocalDate.parse( paymentDueDate ) );
				break;
			case TermsGateway.PaymentType.CREDIT :
				payment = new CreditPayment( LocalDate.parse( paymentDueDate ) );
				break;
			case TermsGateway.PaymentType.LETTER_OF_CREDIT :
				payment = new LetterOfCreditPayment( LocalDate.parse( paymentDueDate ) );
				break;
		}
		return payment;
	}

}
