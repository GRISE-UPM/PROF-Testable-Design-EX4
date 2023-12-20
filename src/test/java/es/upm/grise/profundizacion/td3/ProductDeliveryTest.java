package es.upm.grise.profundizacion.td3;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	private final Order ordermock = mock(Order.class);
    private final Vector<Order> vectormock = mock(Vector.class);



	@BeforeEach
	public void setUp() throws DatabaseProblemException{
		        productDelivery = new ProductDelivery();
	}
	@Test
	public void exceptiondatabase() throws DatabaseProblemException, SQLException{
		DriverManager mockDatabaseConnection = mock(DriverManager.class);
        when(mockDatabaseConnection.getConnection(null)).thenThrow(new DatabaseProblemException());
        assertThrows(DatabaseProblemException.class,()-> new ProductDelivery(mockDatabaseConnection));

	}
	@Test
	public void testmayor100() throws MissingOrdersException  {
        when(ordermock.getAmount()).thenReturn(50.0,40.0,20.0);
		when(vectormock.size()).thenReturn(3);
		productDelivery.setOrders(vectormock);
	
		assertEquals(108.9, productDelivery.calculateHandlingAmount());
	}
    
	@Test
	public void testmenor100() throws MissingOrdersException  {
        when(ordermock.getAmount()).thenReturn(50.0,30.0,10.0);
		when(vectormock.size()).thenReturn(3);
		productDelivery.setOrders(vectormock);
	
		assertEquals(80, productDelivery.calculateHandlingAmount());
	}

}
