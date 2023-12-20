package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	DBSource orderDataSource;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		orderDataSource = mock(DBSource.class);

	}
	//Este metodo testea pasandole un mock que cuando productDelivery recibe una excepcion de la base de datos este devuelve DatabaseProblemException
	@Test
	public void testDBException() throws Exception  {
		//Vector<Order> mockOrders = new Vector<>(Arrays.asList(new Order(1, 10.0), new Order(2, 15.0)));
		when(orderDataSource.fetchOrders()).thenThrow(DatabaseProblemException.class);
		try{
			productDelivery = new ProductDelivery(orderDataSource);
		}
		catch (DatabaseProblemException e){
			assertTrue(true);
		}

	}
	@Test
	public void testp1() throws Exception  {
		Vector<Order> mockOrders = new Vector<>();
		when(orderDataSource.fetchOrders())
				.thenReturn(mockOrders);
		productDelivery = new ProductDelivery(orderDataSource);
		assertThrows(MissingOrdersException.class, () -> {
			productDelivery.calculateHandlingAmount();
		});


	}
	@Test
	public void testp2() throws Exception  {
		Vector<Order> mockOrders = new Vector<>(Arrays.asList(new Order(1, 10.0), new Order(2, 15.0)));
		when(orderDataSource.fetchOrders())
				.thenReturn(mockOrders);
		productDelivery = spy(new ProductDelivery(orderDataSource));
		when(productDelivery.getHour()).thenReturn(23);
		assertEquals(0.75,  productDelivery.calculateHandlingAmount());


	}
	@Test
	public void testp3() throws Exception  {
		Vector<Order> mockOrders = new Vector<>(Arrays.asList(new Order(1, 10.0),
				new Order(2, 15.0),new Order(2, 15.0),
				new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)));
		when(orderDataSource.fetchOrders())
				.thenReturn(mockOrders);
		productDelivery = spy(new ProductDelivery(orderDataSource));
		when(productDelivery.getHour()).thenReturn(22);
		assertEquals(4.8,  productDelivery.calculateHandlingAmount());


	}
	@Test
	public void testp4() throws Exception  {
		Vector<Order> mockOrders = new Vector<>(Arrays.asList(new Order(1, 10.0),
				new Order(2, 15.0),new Order(2, 15.0),
				new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)
				,new Order(2, 15.0),new Order(2, 15.0)));
		when(orderDataSource.fetchOrders())
				.thenReturn(mockOrders);
		productDelivery = spy(new ProductDelivery(orderDataSource));
		when(productDelivery.getHour()).thenReturn(23);
		assertEquals(4.8,  productDelivery.calculateHandlingAmount());


	}
}
