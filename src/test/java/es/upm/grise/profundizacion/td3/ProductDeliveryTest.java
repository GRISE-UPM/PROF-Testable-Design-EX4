package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
public class ProductDeliveryTest {

	ProductDelivery productDelivery;

	@SystemStub
	private EnvironmentVariables variables = new EnvironmentVariables("database.location","jdbc:sqlite:resources/orders.db");

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
	public void test() throws MissingOrdersException {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

}
