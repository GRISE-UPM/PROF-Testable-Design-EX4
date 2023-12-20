package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
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

}
