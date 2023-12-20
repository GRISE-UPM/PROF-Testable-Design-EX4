package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Vector;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductDeliveryTest {
	

	ProductDelivery productDelivery;

	@Mock
	DBUtilities dbUtilities;
	
	
	@Test
	public void whenDBError_throwDatabaseProblemException() throws DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenThrow(DatabaseProblemException.class);
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(dbUtilities)
		, "DatabaseProblemException should be thrown");
	}


	@Test
	//path 1_2_3_9
	public void whenOrdersIsEmpty_throwMissingOrdersException() throws DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenReturn(new Vector<>());
		ProductDelivery productDelivery = new ProductDelivery(dbUtilities);	
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount()
		, "MissingOrdersException should be thrown");
	}

	private Vector<Order> createOrders(int size){
		Vector<Order> orders = new Vector<>();
		for (int i = 0; i < size; i++) {
			orders.add(new Order(i, 2));
		}
		return orders;
	}

	@Test
	//path 1_2_4_5_4_6_7_8_9
	public void test_path_1_2_4_5_4_6_7_8_9() throws MissingOrdersException, DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenReturn(this.createOrders(11));
		ProductDelivery productDelivery = Mockito.spy(new ProductDelivery(dbUtilities));
		doReturn(23).when(productDelivery).getHour(any(),any());
		double expectedAmount = 0.6599999999999999;	
		assertEquals(expectedAmount, productDelivery.calculateHandlingAmount());
	}

	@Test
	//path 1_2_4_5_4_6_7_9
	public void test_path_1_2_4_5_4_6_7_9() throws MissingOrdersException, DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenReturn(this.createOrders(9));
		ProductDelivery productDelivery = Mockito.spy(new ProductDelivery(dbUtilities));
		doReturn(20).when(productDelivery).getHour(any(),any());
		double expectedAmount = 0.36;
		assertEquals(expectedAmount, productDelivery.calculateHandlingAmount());
	}
	


	



	

}
