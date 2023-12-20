package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	AdminDriver adminDriver;
	Vector<Order> order;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		adminDriver = mock(AdminDriver.class);
		order = mock(Vector.class);
	}
	
	/* 
	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}*/

	// Hacer un test que verifica que el constructor ProductDelivery() lanza una excepción de tipo DatabaseProblemException usando mock
	@Test
	public void testDatabaseProblemException() throws DatabaseProblemException, SQLException {
		when(adminDriver.getConnection("jdbc:sqlite:resources/orders.db")).thenThrow(new DatabaseProblemException());
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(adminDriver));
	}

	// Hacer un test por cada camino báscio 
	/*
	C1 = 49-51, 52, 53, EXIT
	C2 = 49-51, 52, 54-59, 61-74a, 75, 76-79, EXIT
	C3 = 49-51, 52, 54-59, 60, 61-74a, 75, 76-79, EXIT
	C4 = 49-51, 52, 54-59, 60, 61-74a, 74b, 75, 76-79, EXIT
	C5 = 49-51, 52, 54-59, 60, 61-74a, 74b, 76-79, EXIT */

	// C1 = salta la excepcion MissingOrdersException del metodo calculateHandlingAmount()
	@Test
	public void testC1() throws Exception {

		new ProductDelivery(adminDriver).orders.clear();
		assertThrows(MissingOrdersException.class, () -> new ProductDelivery(adminDriver).calculateHandlingAmount());
	}


}
