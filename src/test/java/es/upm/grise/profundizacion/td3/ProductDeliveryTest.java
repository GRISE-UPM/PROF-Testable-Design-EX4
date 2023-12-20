package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ProductDeliveryTest {

	ProductDelivery productDelivery;

	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = spy(new ProductDelivery(new ProductDelivery.mockConnection()));
	}

	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}



	@Test
	public void testDosTrue() throws MissingOrdersException {
		doReturn(29).when(productDelivery).HourGetter(any(),any());
		doReturn(16).when(productDelivery).numberOrderGetter();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}
	@Test
	public void testPrimeroFalso() throws MissingOrdersException {
		doReturn(12).when(productDelivery).HourGetter(any(),any());
		doReturn(16).when(productDelivery).numberOrderGetter();
		assertEquals(30,productDelivery.calculateHandlingAmount());
	}
	@Test
	public void testFalsoFalso() throws MissingOrdersException {
		doReturn(12).when(productDelivery).HourGetter(any(),any());
		doReturn(7).when(productDelivery).numberOrderGetter();
		assertEquals(20,productDelivery.calculateHandlingAmount());
	}

}