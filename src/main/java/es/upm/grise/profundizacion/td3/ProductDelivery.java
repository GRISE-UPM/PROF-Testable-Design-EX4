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
	
	public ProductDelivery(DBUtilities dbUtility) throws DatabaseProblemException {
		this.orders = dbUtility.getOrders();
	}

	// Calculate the handling amount
	public double calculateHandlingAmount() throws MissingOrdersException { //1
		
		// This method can only be invoked when there are orders to process
		if(orders.isEmpty()) //2
			throw new MissingOrdersException(); //3
		
		// The handling amount is 2% of the orders' total amount
		double handlingPercentage = SystemConfiguration.getInstance().getHandlingPercentage(); //
																							   // 4	
		double totalAmount = 0;                                                                //
		for(Order order : orders) {                                                            //
			totalAmount += order.getAmount();		//5		                                   
		}
		
		// However, it increases depending on the time of the day
		// We need to know the hour of the day. Minutes and seconds are not relevant
		SimpleDateFormat sdf = new SimpleDateFormat("HH");	        //
		Timestamp timestap = new Timestamp(System.currentTimeMillis());     //
		int hour = this.getHour(sdf, timestap);                   //
																			//    6
			                                                                //
		// and it also depends on the number of orders                      //
		int numberOrders = orders.size();                                   //
		
		// When it is late and the number of orders is large
		// the handling costs more
		if(hour >= 22 //6
			|| numberOrders > 10) { //7
			handlingPercentage += 0.01; //8
		}

		// The final handling amount
		return totalAmount * handlingPercentage; //
		                                         // 9
	}                                            //

	public int getHour(SimpleDateFormat sdf ,Timestamp timestap) {
        return Integer.valueOf(sdf.format(timestap));
    }

}
