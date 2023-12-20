package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDeliveryTest {

	ProductDelivery productDelivery;
	Connector connector;

	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		connector = mock(Connector.class);
	}

	@Test
	public void test() throws MissingOrdersException {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

	@Test
	public void dataBaseProblemException() throws DatabaseProblemException, SQLException {
		when(connector.getConnection(anyString())).thenThrow(new DatabaseProblemException());

		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery(connector);
		});
	}

}
