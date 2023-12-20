package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = new ProductDelivery();
	}
	
	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

	/*
	 * Comprueba que se lanza una excepción de tipo DatabaseProblemException 
	 * cuando se produce un error al acceder a la base de datos.
	 * Para ello, se usa un mock de la clase DriverManager 
	 * que lance una excepción de tipo SQLException cuando se invoque 
	 * el método getConnection().
	 */
	@Test
    public void testDatabaseErrorThrowsException() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            SQLException sqlException = new SQLException("Database error");
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString())).thenThrow(sqlException);

            assertThrows(DatabaseProblemException.class, () -> new ProductDelivery());
        }
    }

	@Test
	public void testCalculateHandlingAmount_NoOrders() throws NoSuchFieldException, IllegalAccessException {
		Field field = ProductDelivery.class.getDeclaredField("orders");
		field.setAccessible(true);
		field.set(productDelivery, new Vector<>());

		assertThrows(MissingOrdersException.class, () -> {
			productDelivery.calculateHandlingAmount();
		});
	}

}
