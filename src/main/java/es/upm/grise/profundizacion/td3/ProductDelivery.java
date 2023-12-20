package es.upm.grise.profundizacion.td3;

import java.util.Vector;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProductDelivery {
	
	private Vector<Order> orders = new Vector<Order>();
	
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
	public double calculateHandlingAmount() throws MissingOrdersException {
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty()) //Nodo 1
			throw new MissingOrdersException(); //Nodo 2
		
		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage(); //Nodo 3
		
		double totalAmount = 0;
		for(Order order : orders) { //Nodo 4
			totalAmount += order.getAmount(); //Nodo 5			
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH"); //Nodo 6
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = Integer.valueOf(sdf.format(timestap));
			
		// and it also depends on the number of orders
		int numberOrders = orders.size();
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 || numberOrders > 10) { //Nodo 7 + 8
			handlingPercentage += 0.01; //Nodo 9
		}

		// The final handling amount
		return totalAmount * handlingPercentage; //Nodo 10
		
	}

	
}
