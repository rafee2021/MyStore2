package com.product.dao;

import java.sql.*;
import java.util.*;

import com.product.model.Product;

//adds CRUD operations to application
public class ProductDao {

	// jdbc authorization
	private String jdbcURL = "jdbc:mysql://localhost:3306/inventory?useSSL=false";
	private String username = "root";
	private String password = "root";
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	// jdbc queries
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO products" + " (brand, name, quantity) VALUES "
			+ " (?, ?, ?);";
	private static final String SELECT_BY_ID = "select id, brand, name, quantity from products where id=?";
	private static final String SELECT_ALL_PRODUCTS = "select * from products";
	private static final String DELETE_PRODUCTS = "delete from products where id = ?;";
	private static final String UPDATE_PRODUCTS = "update products set brand =?, name =?, quantity =? where id =?;";

	// gets jdbc connection
	public ProductDao() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("jdbcDriver");
			connection = DriverManager.getConnection(jdbcURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;

	}

	// CRUD operation methods

	// insert product
	public void insertProduct(Product product) {
		System.out.println(INSERT_PRODUCT_SQL);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
			preparedStatement.setString(1, product.getBrand());
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(1, product.getQuantity());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	// select product by id
	public Product selectProduct(int id) {
		Product product = null;
		// establish connection
		try (Connection connection = getConnection();
				// create connection with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// execute/update query
			ResultSet rs = preparedStatement.executeQuery();

			// process ResultSet object
			while (rs.next()) {
				String brand = rs.getString("brand");
				String name = rs.getString("name");
				String quantity = rs.getString("quantity");
				product = new Product(id, brand, name, quantity);
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		return product;
	}

	// select all products
	public List<Product> selectAll() {

		List<Product> products = new ArrayList<>();
		try (Connection connection = getConnection();
				// create connection with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
			System.out.println(preparedStatement);
			// execute/update query
			ResultSet rs = preparedStatement.executeQuery();

			// process ResultSet object
			while (rs.next()) {
				int id = rs.getInt("id");
				String brand = rs.getString("brand");
				String name = rs.getString("name");
				String quantity = rs.getString("quantity");
				products.add(new Product(id, brand, name, quantity));
			}

		} catch (SQLException e) {
			printSQLException(e);
		}

		return products;

	}

	// update product
	public boolean updateProduct(Product product) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				// create connection with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTS)) {
			System.out.println("updated product: " + preparedStatement);
			preparedStatement.setString(1, product.getBrand());
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(1, product.getQuantity());
			preparedStatement.setInt(4, product.getId());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}

		return rowUpdated;

	}

	// delete product
	public boolean deleteProduct(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				// create connection with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTS)) {
			preparedStatement.setInt(1, id);

			rowDeleted = preparedStatement.executeUpdate() > 0;
		}

		return rowDeleted;

	}

	// for exception throwable
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message" + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}

			}
		}

	}

}
