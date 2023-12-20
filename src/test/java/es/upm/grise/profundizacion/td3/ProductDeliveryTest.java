package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Driver;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	AdminDriver adminDriver;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		adminDriver = mock(AdminDriver.class);
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

}
