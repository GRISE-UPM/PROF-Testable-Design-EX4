package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = new ProductDelivery(new ProductDelivery.mockClass());
	}


	@Test
	public void testDatabaseConnectionError() throws DatabaseProblemException {
		ProductDelivery.mockClass mockWrapper = mock(ProductDelivery.mockClass.class);
		Mockito.when(mockWrapper.getConnection(Mockito.anyString()))
				.thenThrow(new DatabaseProblemException());

		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery(mockWrapper);
		});
	}


	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

}
