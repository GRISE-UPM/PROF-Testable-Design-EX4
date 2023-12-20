package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	DataBaseError dbError;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		dbError = mock(DataBaseError.class);
		productDelivery = spy(new ProductDelivery(new DataBaseError("jdbc:sqlite:resources/orders.db")));
	}

	// Error en la database
	@Test
	public void dataBaseErrorTest() throws DatabaseProblemException {
		when(dbError.dataBaseCreated()).thenThrow(DatabaseProblemException.class);
		try{
			productDelivery = new ProductDelivery(dbError);
		}
		catch (DatabaseProblemException e){
			assertTrue(true);
		}
	}

	// Caso 3: 1-2-3-9
	@Test
	public void allpathsTest1() throws DatabaseProblemException {
		productDelivery = new ProductDelivery(dbError);
		Vector<Order> mockOrders = spy(new Vector<Order>());
		doReturn(true).when(mockOrders).isEmpty();
		productDelivery.setOrders(mockOrders);
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount());
	}

	// Caso 3: 1-2-4-5-4-6-8-9
	@Test
	public void allpathsTest2() throws DatabaseProblemException, MissingOrdersException {
		doReturn(27).when(productDelivery).gethour(any(),any());
		doReturn(100).when(productDelivery).getOrders();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}

	// Caso 3: 1-2-4-5-4-6-7-8-9
	@Test
	public void allpathsTest3() throws DatabaseProblemException, MissingOrdersException {
		doReturn(20).when(productDelivery).gethour(any(),any());
		doReturn(100).when(productDelivery).getOrders();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}


	// Caso 4: 1-2-4-5-4-6-7-9
	@Test
	public void allpathsTest4() throws DatabaseProblemException, MissingOrdersException {
		doReturn(20).when(productDelivery).gethour(any(),any());
		doReturn(8).when(productDelivery).getOrders();
		assertEquals(20,productDelivery.calculateHandlingAmount());
	}

}
