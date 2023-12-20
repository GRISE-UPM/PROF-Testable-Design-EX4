package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Vector;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = spy(new ProductDelivery(new ProductDelivery.mockClass()));
	}


	@Test
	public void testDatabaseConnectionError() throws DatabaseProblemException {
		ProductDelivery.mockClass mockWrapper = mock(ProductDelivery.mockClass.class);
		Mockito.when(mockWrapper.getConnection(Mockito.anyString()))
				.thenThrow(new DatabaseProblemException());

		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery(mockWrapper);
		});
	}


	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

	@Test
	// Comprueba que se tire la excepcion
	public void testThrowsException() throws MissingOrdersException  {
		Vector<Order> mockedOrders = spy (new Vector<Order>());
		doReturn(true).when(mockedOrders).isEmpty();
		productDelivery.setOrders(mockedOrders);
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount());
	}

	@Test
	public void testGoesinIFBothTrue() throws MissingOrdersException {
		doReturn(23).when(productDelivery).getHour(any(),any());
		doReturn(11).when(productDelivery).getnumberOrders();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}

	@Test
	public void testGoesinIFFirstFalse() throws MissingOrdersException {
		doReturn(20).when(productDelivery).getHour(any(),any());
		doReturn(11).when(productDelivery).getnumberOrders();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}
	@Test
	public void testGoesinIFFalseFalse() throws MissingOrdersException {
		doReturn(21).when(productDelivery).getHour(any(),any());
		doReturn(9).when(productDelivery).getnumberOrders();
		assertEquals(20,productDelivery.calculateHandlingAmount());
	}



}
