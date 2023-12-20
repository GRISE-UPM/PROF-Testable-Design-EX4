package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;

	@BeforeEach
	public void setUp() throws DatabaseProblemException{
		productDelivery = new ProductDelivery(new ProductDelivery.dummyConnectionClass());
	}

	@Test
	public void testDatabaseError() throws DatabaseProblemException {
		ProductDelivery.dummyConnectionClass dummy = mock(ProductDelivery.dummyConnectionClass.class);
		Mockito.when(dummy.getConnection(anyString())).thenThrow(new DatabaseProblemException());
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(dummy));
	}

	@Test
	public void testFirstPath() throws MissingOrdersException {
		// Se prueba el primer camino, donde las orders son cero
		Vector<Order> mockedOrders = spy (new Vector<Order>());

		try {
			productDelivery = new ProductDelivery(new ProductDelivery.dummyConnectionClass());
		} catch (DatabaseProblemException e) {
			fail("Unexpected DatabaseProblemException");
		}

		doReturn(true).when(mockedOrders).isEmpty();
		productDelivery.orders = mockedOrders;
		assertThrows(MissingOrdersException.class, () -> {
                productDelivery.calculateHandlingAmount();
            });
	}

	@Test
	public void testSecondPath() throws MissingOrdersException {
		// Se pasa por 6a y va a 7, es decir, que haya 10 o menos orders

		ProductDelivery spyDelivery = spy(productDelivery);

		doReturn(22).when(spyDelivery).getHour(any(),any());
		doReturn(9).when(spyDelivery).getOrders(spyDelivery.orders);
		assertEquals(30, spyDelivery.calculateHandlingAmount());
	}

	@Test
	public void testThirdPath() throws MissingOrdersException {
		// Se pasa por 6a y 6b, y se sale, es decir, que hour < 22 y orders <= 10

		ProductDelivery spyDelivery = spy(productDelivery);

		doReturn(10).when(spyDelivery).getHour(any(),any());
		doReturn(9).when(spyDelivery).getOrders(spyDelivery.orders);
		assertEquals(20, spyDelivery.calculateHandlingAmount());
	}

	@Test
	public void testFourthPath() throws MissingOrdersException {
		// Se pasa por nodo 7 tras 6b, y se acaba, o sea, hay >= 22 hours y > 10 orders

		ProductDelivery spyDelivery = spy(productDelivery);

		doReturn(22).when(spyDelivery).getHour(any(),any());
		doReturn(15).when(spyDelivery).getOrders(spyDelivery.orders);
		assertEquals(30, spyDelivery.calculateHandlingAmount());
	}
	
}
