package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		// productDelivery = new ProductDelivery();
	}

	@Test 
	public void test1() throws DatabaseProblemException, SQLException{
		Connection mockConnection = Mockito.mock(Connection.class);

		// Simulamos un error de base de datos
		Mockito.when(mockConnection.createStatement()).thenThrow(new SQLException());
			
		// Comprobaciones
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(mockConnection));
		verify(mockConnection,times(1)).createStatement();

}

	
	@Test
	public void test() throws MissingOrdersException  {
		// assertEquals(20, productDelivery.calculateHandlingAmount());
	}

}
