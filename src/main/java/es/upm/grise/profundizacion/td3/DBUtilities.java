package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class DBUtilities {
    
    private final String DB_URL = "jdbc:sqlite:resources/orders.db";

    public Vector<Order> getOrders() throws DatabaseProblemException {
        Vector<Order> orders = new Vector<>();
        // Orders are loaded into the orders vector for processing
		try {	
			// Create DB connection
			Connection connection = DriverManager.getConnection(DB_URL);

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
        return orders;
    }
}
