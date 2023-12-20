package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProductDeliveryTest {

	ProductDelivery productDelivery;
	CustomDriverManager driver;

	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		driver = mock(CustomDriverManager.class);
	}

	@Test
	public void test() throws MissingOrdersException {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

	@Test
	public void dataBaseProblemException() throws DatabaseProblemException, SQLException {
		when(driver.getConnection(anyString())).thenThrow(new DatabaseProblemException());

		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery(driver);
		});
	}

}
