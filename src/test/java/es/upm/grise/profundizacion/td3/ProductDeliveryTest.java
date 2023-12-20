package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import java.sql.DriverManager;

public class ProductDeliveryTest {
	
    @Mock
    private DriverManager driverManager;
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		productDelivery = new ProductDelivery();
	}
	
	@Test
	public void test() throws MissingOrdersException  {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

    //Pregunta 4
    @Test
    public void testCalculateHandlingAmountWithEmptyOrders() {
        try {
            productDelivery.orders.clear();

            productDelivery.calculateHandlingAmount();

            fail("Expected MissingOrdersException to be thrown");
        } catch (MissingOrdersException e) {
            // Test passed
        }
    }

}
