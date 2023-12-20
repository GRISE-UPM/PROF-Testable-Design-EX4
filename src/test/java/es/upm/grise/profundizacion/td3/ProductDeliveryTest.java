package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testCalculateHandelingThrowMissingOrdersException() {
        productDelivery.orders.clear();

        assertThrows(MissingOrdersException.class, () -> {
                productDelivery.calculateHandlingAmount();
            });
    }

}
