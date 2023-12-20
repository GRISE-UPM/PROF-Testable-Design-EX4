package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	// @Test
	// public void testDataBaseProblemException() {
	// 	DriverManager mockMannager=mock(DriverManager.class);
		
		
	// 	assertThrows(DatabaseProblemException.class, () -> {
	// 		new ProductDelivery();
	// 	});
	// }

	@Test
	public void testCamino1(){
		productDelivery.orders.clear();

		assertThrows(MissingOrdersException.class, ()->{
			productDelivery.calculateHandlingAmount();});
	}

	@Test
	public void testCamino2(){
		SimpleDateFormat mockDataFormaInteger=mock(SimpleDateFormat.class);
		when(mockDataFormaInteger.format(anyLong())).thenReturn("23");

		try {
			assertTrue(productDelivery.calculateHandlingAmount()==20.);
		} catch (MissingOrdersException e) {
			fail();
			e.printStackTrace();
		}
	}

	
}
