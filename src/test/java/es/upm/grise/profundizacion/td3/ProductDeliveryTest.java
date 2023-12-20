package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = new ProductDelivery("jdbc:sqlite:resources/orders.db");
	}
	
	@Test
	public void testDbError() throws DatabaseProblemException, SQLException  {
		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery("dummy");
		});
	}

	@Test
	public void testCalculateThrowsError() throws MissingOrdersException {
		productDelivery.orders.clear();
		assertThrows(MissingOrdersException.class, () -> {
			productDelivery.calculateHandlingAmount();
		});
	}
}
