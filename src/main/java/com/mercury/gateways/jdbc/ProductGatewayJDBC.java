package com.mercury.gateways.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mercury.model.Product;
import com.mercury.gateways.ProductGateway;
import com.mercury.gateways.Queries;

public class ProductGatewayJDBC extends jdbcGateway implements ProductGateway {

	public ProductGatewayJDBC(String configFIle) {
		super(configFIle);
	}

	@Override
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> products = new ArrayList<>();

		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(Queries.Products.getAllProducts);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				products.add(createProductFromResultSet(resultSet));
			}
		} catch (Exception e) {
			logger.error("Unable to get products [" + e.getMessage() + "]");
		} finally {
			closeStatement(preparedStatement);
		}
		return products;
	}

	public Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
		return createProductFromResultSetWithPrefix(resultSet, "");
	}

	public Product createProductFromResultSetWithPrefix(ResultSet resultSet, String prefix) throws SQLException {
		return new Product(resultSet.getLong( prefix + "id" ), resultSet.getString( prefix + "name" ));
	}

}
