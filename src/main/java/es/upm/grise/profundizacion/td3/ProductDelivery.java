package es.upm.grise.profundizacion.td3;

import java.util.Vector;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDelivery  {
	
	private Vector<Order> orders = new Vector<Order>();
	private DatabaseConnection databaseConnection;
	
	public ProductDelivery(DatabaseConnection databaseConnection) throws DatabaseProblemException {
        this.databaseConnection = databaseConnection;
        loadOrders();
    }
	
	private void loadOrders() throws DatabaseProblemException {
		Connection connection = null;
		try {
			// Get the database connection
			connection = databaseConnection.getConnection();
	
			// Read from the orders table
			String query = "SELECT * FROM orders";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
	
			// Iterate until we get all orders' data
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				double amount = resultSet.getDouble("amount");
				orders.add(new Order(id, amount));
			}
		} catch (SQLException e) {
			throw new DatabaseProblemException();
		} finally {
			// Close the connection in the finally block to ensure it's always closed
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Log or handle the exception if necessary
				}
			}
		}
	}
	
	
	public ProductDelivery() throws DatabaseProblemException {
		
		// Orders are loaded into the orders vector for processing
		try {
			
			// Create DB connection
			Connection connection = DriverManager.getConnection("jdbc:sqlite:resources/orders.db");

			// Read from the orders table
			String query = "SELECT * FROM orders";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			// Iterate until we get all orders' data
			while (resultSet.next()) {
				
				int id = resultSet.getInt("id");
				double amount = resultSet.getDouble("amount");
				orders.add(new Order(id, amount));
				
			}

			// Close the connection
			connection.close();

		} catch (Exception e) {
			
			throw new DatabaseProblemException(); 
			
		}

	}

	// Calculate the handling amount
	public double calculateHandlingAmount() throws MissingOrdersException { //NODO 1
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty()) //NODO 2
			throw new MissingOrdersException(); //NODO 3
		
		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage();//NODO 4
		
		double totalAmount = 0;
		for(Order order : orders) {//NODO 4
			totalAmount += order.getAmount();	//NODO 5			
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH");				//NODO 6
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = Integer.valueOf(sdf.format(timestap));
			
		// and it also depends on the number of orders
		int numberOrders = orders.size();
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 /*NODO 6a*/|| numberOrders > 10/*NODO 6b*/) {
			handlingPercentage += 0.01; //NODO 7
		}

		// The final handling amount
		return totalAmount * handlingPercentage; //NODO 8
		
	}

	
}