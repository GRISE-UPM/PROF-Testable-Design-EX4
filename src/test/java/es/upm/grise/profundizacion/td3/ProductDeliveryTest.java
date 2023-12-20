package es.upm.grise.profundizacion.td3;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;

	@BeforeEach
	public void setUp() throws DatabaseProblemException{
		        productDelivery = new ProductDelivery();
	}
	@Test 
	public void exceptiondatabase() throws DatabaseProblemException, SQLException{
		DriverManager mockDatabaseConnection = mock(DriverManager.class);
        when(mockDatabaseConnection.getConnection(null)).thenThrow(new DatabaseProblemException());

        // Creating the ProductDelivery instance with the mocked DatabaseConnection
        ProductDelivery productDelivery = new ProductDelivery(mockDatabaseConnection);

	}
	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

}
