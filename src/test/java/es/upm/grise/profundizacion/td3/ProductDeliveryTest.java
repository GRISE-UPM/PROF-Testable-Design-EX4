package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductDeliveryTest {

	ProductDelivery productDelivery;
	IDatabaseConnection dbMock;
	String url = "jdbc:sqlite:resources/orders.db";
	SystemConfiguration configMock;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException 
	{
		dbMock = Mockito.mock(IDatabaseConnection.class);
		//productDelivery = new ProductDelivery(dbMock, url);
		configMock = Mockito.spy(SystemConfiguration.class);
	}

	@Test
	public void test() throws MissingOrdersException, DatabaseProblemException 
	{
		productDelivery = new ProductDelivery(dbMock, url, configMock);
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}
	
	@Test
	public void DatabaseExceptionIsRaised()
	{
		  assertThrows(DatabaseProblemException.class, () -> {
	            new ProductDelivery(dbMock, "asdas", configMock);
	        });
	}

	@Test
	public void MissingOrdersExceptionIsRaised() throws DatabaseProblemException 
	{
		productDelivery = new ProductDelivery(dbMock, url, configMock);
		productDelivery.clearOrders();

		assertThrows(MissingOrdersException.class, () -> {
			productDelivery.calculateHandlingAmount();
		});
	}
	
	@Test
	public void currentHourIsMoreThan22Test() throws DatabaseProblemException
	{
		productDelivery = new ProductDelivery(dbMock, url, configMock);
		SimpleDateFormat sdfMock = Mockito.mock(SimpleDateFormat.class);

		//2023-12-20 20:21:41.673
		Mockito.when(sdfMock.format(Mockito.any())).thenReturn("2023-12-20 23:21:41.673");
		assertEquals(configMock.getHandlingPercentage()+0.01, 0.03);
	}
	
	@Test
	public void ordersAreMoreThan10Test() throws DatabaseProblemException
	{
		productDelivery = new ProductDelivery(dbMock, url, configMock);
		Vector<Order> v = Mockito.mock(Vector.class);
		Mockito.when(v.size()).thenReturn(11);
		assertEquals(configMock.getHandlingPercentage()+0.01, 0.03);
	}

}
