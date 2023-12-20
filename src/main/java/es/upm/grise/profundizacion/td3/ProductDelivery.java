package es.upm.grise.profundizacion.td3;

import java.util.Vector;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDelivery {
	
	private Vector<Order> orders = new Vector<Order>();

	/*
	 * Para permitir el mocking de la clase DriverManager,
	 * se saca getConnection del contructor y se 
	 * crea un metodo protected que devuelve una conexion
	 */
	protected Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:resources/orders.db");
    }
	
	public ProductDelivery() throws DatabaseProblemException {
		
		// Orders are loaded into the orders vector for processing
		try {
			
			// Create DB connection
			//Connection connection = DriverManager.getConnection("jdbc:sqlite:resources/orders.db");
			Connection connection = createConnection();

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
	//Nodo 1
	public double calculateHandlingAmount() throws MissingOrdersException {
		
		// This method can only be invoked when there are orders to process
		//Nodo 2
		if(orders.isEmpty())
			//Nodo 3
			throw new MissingOrdersException();
		
		// The handling amount is 2% of the orders' total amount
		//Nodo 4
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage();
		
		double totalAmount = 0;
		for(Order order : orders) {
			//Nodo 5
			totalAmount += order.getAmount();				
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		//Nodo 6
		SimpleDateFormat sdf = new SimpleDateFormat("HH");	
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = Integer.valueOf(sdf.format(timestap));
			
		// and it also depends on the number of orders
		int numberOrders = orders.size();
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 || numberOrders > 10) { //Nodo 7 la segunda condicion
			//Nodo 8
			handlingPercentage += 0.01;
		}

		// The final handling amount
		//Nodo 9
		return totalAmount * handlingPercentage;
		
	}

	
}
