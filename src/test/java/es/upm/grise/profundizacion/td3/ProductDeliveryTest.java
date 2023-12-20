package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import es.upm.grise.profundizacion.ProducDeliveryDate;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
public class ProductDeliveryTest {

	ProductDelivery productDelivery;

	@SystemStub
	private EnvironmentVariables variables = new EnvironmentVariables("database.location",
			"jdbc:sqlite:resources/orders.db");

	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		this.productDelivery = new ProductDelivery();
	}

	@Test
	public void testDatabaseProblemException() throws DatabaseProblemException {
		assertThrows(DatabaseProblemException.class, () -> {
			this.variables.set("database.location", null);
			new ProductDelivery();
		}, "Expected throw when no database.location env var found");
	}

	@Test
	public void testMoreThan10Orders() throws MissingOrdersException {
		for (int i = 0; i < 10; i++) {
			this.productDelivery.orders.add(new Order(i, 10));
		}
		assertEquals(33, productDelivery.calculateHandlingAmount());
	}

	@Test
	public void testMoreThan22Hours() throws MissingOrdersException {
		ProducDeliveryDate pdd = mock(ProducDeliveryDate.class);
		when(pdd.getTime()).thenReturn(23);
		this.productDelivery.orders.add(new Order(1, 10));
		assertEquals(20.2, productDelivery.calculateHandlingAmount());
	}

	@Test
	public void testOrdersEmpty() throws MissingOrdersException {
		this.productDelivery.orders = new Vector<Order>();
		assertThrows(MissingOrdersException.class, () -> {
			assertEquals(33, productDelivery.calculateHandlingAmount());
		}, "Expected throw when orders is empty");
	}

	@Test
	public void test() throws MissingOrdersException {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

}
