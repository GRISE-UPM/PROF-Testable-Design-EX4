package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.Connection;
import java.util.Vector;

@ExtendWith(MockitoExtension.class)
public class ProductDeliveryTest {

	@Mock
	private Connection connection;
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
	public void testCalculateHandlingAmount_shouldThrownMissingOrdersException() {
		productDelivery.orders = new Vector<Order>();
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount());
}

}
