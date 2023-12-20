package es.upm.grise.profundizacion.td3;

import java.util.Vector;

import es.upm.grise.profundizacion.ProducDeliveryDate;

import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProductDelivery {
	
	public Vector<Order> orders = new Vector<Order>();
	
	public ProductDelivery() throws DatabaseProblemException {
		
		// Orders are loaded into the orders vector for processing
		try {
			System.setProperty("database.location", "jdbc:sqlite:resources/orders.db");
			String database_location = System.getenv("database.location");
			
			// Create DB connection
			Connection connection = DriverManager.getConnection(database_location);

			// Read from the orders table
			String query = "SELECT * FROM orders";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			// Iterate until we get all orders' data
			while (resultSet.next()) {
				
				int id = resultSet.getInt("id");
				double amount = resultSet.getDouble("amount");
				this.orders.add(new Order(id, amount));
				
			}

			// Close the connection
			connection.close();

		} catch (Exception e) {
			
			throw new DatabaseProblemException(); 
			
		}

	}

	// Calculate the handling amount
	public double calculateHandlingAmount() throws MissingOrdersException { // 1
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty()) // 2 
			throw new MissingOrdersException(); // 3
		
		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage();
		
		double totalAmount = 0;
		for(Order order : orders) { // 4
			totalAmount += order.getAmount(); // 5		
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		ProducDeliveryDate pdd = new ProducDeliveryDate();
		int hour = pdd.getTime();
			
		// and it also depends on the number of orders
		int numberOrders = orders.size();
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 || numberOrders > 10) { // 6a 6b
			handlingPercentage += 0.01; // 7
		}

		// The final handling amount
		return totalAmount * handlingPercentage; // 8
		
	}

	
}
