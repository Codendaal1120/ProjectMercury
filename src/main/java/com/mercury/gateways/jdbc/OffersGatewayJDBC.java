package com.mercury.gateways.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.mercury.model.Offer;
import com.mercury.model.Product;
import com.mercury.model.SellOffer;
import com.mercury.model.Terms;
import com.mercury.model.Bid;
import com.mercury.model.BuyOffer;
import com.mercury.utilities.DateUtility;
import com.mercury.gateways.OffersGateway;
import com.mercury.gateways.Queries;

public class OffersGatewayJDBC extends jdbcGateway implements OffersGateway {

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
			while (resultSet.next()) {
				offers.add( createOfferFromResultSet(resultSet) );
			}
		} catch (Exception e) {
			logger.error("Unable to get offers [" + e.getMessage() + "]");
		} finally {
			closeStatement(preparedStatement);
		}
		return offers;
	}

	@Override
	public long saveOffer(Offer offerToSave) {
		Connection connection;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Queries.Offers.saveOffer, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, offerToSave.getOwnerId());
			preparedStatement.setInt(2, getOfferType(offerToSave));
			preparedStatement.setLong(3, offerToSave.getProduct().getId());
			preparedStatement.setDouble(4, offerToSave.getQuantity());
			preparedStatement.setInt(5, offerToSave.getStatus());
			preparedStatement.setString(6,
					DateUtility.DateToString(offerToSave.getDelivery().getDeliveryDate(), DateUtility.sqlDateFormat));
			preparedStatement.setLong(7, offerToSave.getListedTerms().getId());
			preparedStatement.setLong(8, getOfferAcceptedBidId(offerToSave));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error("Unable to save offer (" + offerToSave.toJsonString() + ") [" + e.getMessage() + "]");
			return -1;
		} finally {
			closeStatement(preparedStatement);
		}
	}

	private Offer createOfferFromResultSet(ResultSet resultSet) throws SQLException {
		Offer newOffer = null;
		Terms offerTerms = new TermsGatewayJDBC(config).createTermsFromResultSetWithPrefix(resultSet, "listed_");
		Product product = new ProductGatewayJDBC(config).createProductFromResultSetWithPrefix(resultSet, "product_");

		if (resultSet.getInt( "offer_type" ) == Offer.OfferStatus.CLOSED ){
			Bid acceptedBid = null;
			//TODO: load bid from gateway
			newOffer = createOfferWithBid( resultSet.getInt("offer_type"), offerTerms, product, resultSet.getLong("owner_id"), resultSet.getDouble("qty"), DateUtility.StringToDateTime( resultSet.getString("due_date"), DateUtility.sqlDateTimeFormat ), acceptedBid );
		}
		else{
			newOffer = createOffer( resultSet.getInt("offer_type"), offerTerms, product, resultSet.getLong("owner_id"), resultSet.getDouble("qty"), DateUtility.StringToDateTime( resultSet.getString("due_date"), DateUtility.sqlDateTimeFormat ) );
		}
		newOffer.setId( resultSet.getLong("id") );		
		return newOffer;
	}

	private int getOfferType(Offer offer){
		if (offer instanceof BuyOffer){
			return OffersGateway.OfferType.BUY;
		}
		else if (offer instanceof SellOffer){
			return OffersGateway.OfferType.SELL;
		}
		else{
			return -1;
		}
	}

	private long getOfferAcceptedBidId(Offer offer){
		if (offer.getStatus() == Offer.OfferStatus.CLOSED){
			return offer.getAcceptedBid().getId();
		}
		else{
			return -1;
		}
	}

	private Offer createOfferWithBid(int OfferType, Terms listedTerms, Product product, long ownerId, double quantity, LocalDateTime dueDate, Bid accepteBid){
		Offer offer = null;
		switch (OfferType){
			case OffersGateway.OfferType.BUY :			
				offer = new BuyOffer(product, listedTerms, dueDate, quantity, ownerId, accepteBid);
				break;
				case OffersGateway.OfferType.SELL :
				offer = new SellOffer(product, listedTerms, dueDate, quantity, ownerId, accepteBid);
				break;
		}
		return offer;
	}

	private Offer createOffer(int OfferType, Terms listedTerms, Product product, long ownerId, double quantity, LocalDateTime dueDate){
		Offer offer = null;
		switch (OfferType){
			case OffersGateway.OfferType.BUY :			
				offer = new BuyOffer(product, listedTerms, dueDate, quantity, ownerId);
				break;
				case OffersGateway.OfferType.SELL :
				offer = new SellOffer(product, listedTerms, dueDate, quantity, ownerId);
				break;
		}
		return offer;
	}
	
}
