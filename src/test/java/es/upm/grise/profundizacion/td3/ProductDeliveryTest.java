package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ProductDeliveryTest {
	

	ProductDelivery productDelivery;
	
	Vector<Order> orders;
	
	DriverManagerPersonal driverManager;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		driverManager = mock(DriverManagerPersonal.class);
		
	}
	
	@Test
	public void testPregunta3() throws SQLException, DatabaseProblemException {
		
		when(driverManager.getConnection("jdbc:sqlite:resources/orders.db")).thenThrow(new DatabaseProblemException());
		
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(driverManager));
		
	}
	
	@Test
	public void testCamino1() throws MissingOrdersException{
		productDelivery = mock(ProductDelivery.class);
		
		when(productDelivery.isEmpty()).thenReturn(true);
		
	
		assertEquals(MissingOrdersException.class, productDelivery.calculateHandlingAmount());
		
	}
	
	@Test
	public void testCamino3() throws MissingOrdersException{
		productDelivery = mock(ProductDelivery.class);
		
		when(productDelivery.numberOrders()).thenReturn(9);
		
		assertEquals(0.0, productDelivery.calculateHandlingAmount());
		
	}

}
