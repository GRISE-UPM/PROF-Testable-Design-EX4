package es.upm.grise.profundizacion.td3;

import java.sql.*;
import java.util.Vector;
import java.text.SimpleDateFormat;

public class ProductDelivery {
	
	private Vector<Order> orders = new Vector<Order>();
	Connection connection;
	private DataBaseError dbError;
	public void setOrders(Vector<Order> orders){
		this.orders=orders;
	}
	public ProductDelivery(DataBaseError dbError) throws DatabaseProblemException {
		this.dbError= dbError;
		try {
			orders=dbError.dataBaseCreated();
		} catch (Exception e) {
			throw new DatabaseProblemException();
		}
	}

	//Funcion para poder mockear hour (darle el valor que se quiera)
	public int gethour(SimpleDateFormat sdf, Timestamp timestap){
		return Integer.valueOf(sdf.format(timestap));
	}

	//Funcion para poder mockear numberOrders (darle el valor que se quiera)
	public int getOrders(){
		return orders.size();
	}

	// Calculate the handling amount
	public double calculateHandlingAmount() throws MissingOrdersException {//Nodo 1
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty())//Nodo 2
			throw new MissingOrdersException();//Nodo 3
		
		// The handling amount is 2% of the orders' total amount
		//Nodo 4
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage();
		
		double totalAmount = 0;
		for(Order order : orders) {
			totalAmount += order.getAmount();//Nodo 5
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH");	
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = gethour(sdf,timestap);
			
		// and it also depends on the number of orders
		int numberOrders = getOrders();
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 || numberOrders > 10) {//Nodo 6 y 7
			handlingPercentage += 0.01;//Nodo 8
		}

		// The final handling amount
		return totalAmount * handlingPercentage;//Nodo 9
		
	}

	
}
