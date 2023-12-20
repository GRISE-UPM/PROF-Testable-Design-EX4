package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductDeliveryTest {
	
	@InjectMocks
	ProductDelivery productDelivery;

	@Mock
	DBUtilities dbUtilities;
	
	
	@Test
	public void whenDBError_throwDatabaseProblemException() throws DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenThrow(DatabaseProblemException.class);
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(dbUtilities)
		, "DatabaseProblemException should be thrown");
	}

}
