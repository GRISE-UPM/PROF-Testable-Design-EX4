package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = new ProductDelivery();
	}
	
	@Test
	public void testNoDataBase() throws Exception  {
		assertThrowsExactly(DatabaseProblemException.class, () ->
			{new ProductDelivery("wrong_db");});
	}

	@Test
	public void testOrdersEmpty() throws Exception  {
		productDelivery.orders.removeAllElements();
		assertThrowsExactly(MissingOrdersException.class, () ->
			{productDelivery.calculateHandlingAmount();});
	}

	@Test
	public void testNoExtraHandlingCost() throws Exception  {
		int ordersSize = productDelivery.orders.size();
		while(ordersSize > 10) {
			productDelivery.orders.remove(ordersSize - 1);
			ordersSize--;
		}

		double expectedAmount = 0;
		for(Order order : productDelivery.orders)
			expectedAmount += order.getAmount();
		expectedAmount *= SystemConfiguration.getInstance().getHandlingPercentage();

		SimpleDateFormat sdf = mock(SimpleDateFormat.class);
		when(sdf.format(any())).thenReturn("10");
		
		assertEquals(expectedAmount, productDelivery.calculateHandlingAmount(sdf));
	}

}
