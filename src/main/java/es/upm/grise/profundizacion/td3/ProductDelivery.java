package es.upm.grise.profundizacion.td3;

import java.sql.*;
import java.util.Vector;
import java.text.SimpleDateFormat;

public class ProductDelivery {

	// Clase que se puede usar para mockear getConnection
	public static class mockClass {
		public Connection getConnection(String url) throws DatabaseProblemException {
            try {
                return DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new DatabaseProblemException();
            }
        }
	}


	private Vector<Order> orders = new Vector<Order>();
	private mockClass mockClass;

	// Añadido un setter para poder depurar esta parte
	public void setOrders(Vector<Order> newOrder) {
		orders = newOrder;
	}

	// Funcion para poder mockear getHour
	public int getHour(SimpleDateFormat sdf ,Timestamp timestap) {
		return Integer.valueOf(sdf.format(timestap));
	}
	// Funcion para poder mockear NumberOrders
	public int getnumberOrders() {
		return orders.size();
	}

	// Añadimos una dependencia para poder mockear getConnections
	public ProductDelivery(mockClass mockClass) throws DatabaseProblemException {
		this.mockClass = mockClass;
		
		// Orders are loaded into the orders vector for processing
		try {
			
			// Create DB connection
			Connection connection = mockClass.getConnection("jdbc:sqlite:resources/orders.db");

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
	public double calculateHandlingAmount() throws MissingOrdersException {// Nodo 1
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty())// Nodo 2
			throw new MissingOrdersException();// Nodo 3
		// Nodo 4
		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage();
		
		double totalAmount = 0;
		// Nodo 4
		for(Order order : orders) {
			totalAmount += order.getAmount();	// Nodo 5
		}

		// Nodo 6
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH");	
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = getHour(sdf,timestap);
			
		// and it also depends on the number of orders
		int numberOrders = getnumberOrders(); // facil de mockear
		
		// When it is late and the number of orders is large
		// the handling costs more
		// Nodo 6
		if(hour >= 22 || numberOrders > 10) { // Segunda condicion: Nodo 7
			handlingPercentage += 0.01;// Nodo 8
		}

		// The final handling amount
		return totalAmount * handlingPercentage;// Nodo 9
		
	}




}

