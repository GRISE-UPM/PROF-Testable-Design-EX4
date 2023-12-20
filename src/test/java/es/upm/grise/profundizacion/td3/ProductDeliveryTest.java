package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Vector;

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
		
		@Test
	    public void testDatabaseProblemException() throws SQLException, DatabaseProblemException {

	        // Mocking the DatabaseConnection
	        DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
	        when(mockDatabaseConnection.getConnection()).thenThrow(new SQLException("Database connection error"));

	        // Creating the ProductDelivery instance with the mocked DatabaseConnection
	        ProductDelivery productDelivery = new ProductDelivery(mockDatabaseConnection);

	        // The following line should throw DatabaseProblemException
	        assertThrows(DatabaseProblemException.class, () -> productDelivery.calculateHandlingAmount());
	    }
	/*
	@Test(expected = DatabaseProblemException.class)
    public void testDatabaseProblemException() throws SQLException, DatabaseProblemException {

        // Mocking the DatabaseConnection
        DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
        when(mockDatabaseConnection.getConnection()).thenThrow(new SQLException("Database connection error"));

        // Creating the ProductDelivery instance with the mocked DatabaseConnection
        ProductDelivery productDelivery = new ProductDelivery(mockDatabaseConnection);

        // The following line should throw DatabaseProblemException
        productDelivery.calculateHandlingAmount();
    }*/

	@Test
	public void testEmptyOrders() throws MissingOrdersException {
	    // Establecer que la lista de órdenes está vacía
	    this.productDelivery.orders = new Vector<Order>();
	
	    // La siguiente línea debería lanzar MissingOrdersException
	    assertThrows(MissingOrdersException.class, () -> {
	        // Asegurar que se lanza la excepción cuando la lista de órdenes está vacía
	        assertEquals(55, productDelivery.calculateHandlingAmount());
	    }, "Se esperaba una excepción cuando la lista de órdenes está vacía");
	}



}

