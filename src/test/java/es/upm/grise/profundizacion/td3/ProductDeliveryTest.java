package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductDeliveryTest {
	

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


	@Test
	//path 1_2_3_9
	public void whenOrdersIsEmpty_throwMissingOrdersException() throws DatabaseProblemException  {
		Mockito.when(dbUtilities.getOrders())
			.thenReturn(new Vector<>());
		ProductDelivery productDelivery = new ProductDelivery(dbUtilities);	
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount()
		, "MissingOrdersException should be thrown");
	}


	



	

}
