package es.upm.grise.profundizacion.td3;

import java.util.Vector;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProductDelivery {

	private Vector<Order> orders;

	public ProductDelivery(OrdersDao ordersDao) throws DatabaseProblemException {

		orders = ordersDao.getOrders();

	}

	// Calculate the handling amount
	public double calculateHandlingAmount() throws MissingOrdersException {

		// This method can only be invoked when there are orders to process
		if(orders.isEmpty()) // 1
			throw new MissingOrdersException(); // 2

		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage(); //3

		double totalAmount = 0;
		for(Order order : orders) { //4
			totalAmount += order.getAmount(); //5
		}

		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH"); //6
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		int hour = Integer.valueOf(sdf.format(timestap));

		// and it also depends on the number of orders
		int numberOrders = orders.size();

		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 || numberOrders > 10) { //7
			handlingPercentage += 0.01; //8
		}

		// The final handling amount
		return totalAmount * handlingPercentage; //9

	}


}
