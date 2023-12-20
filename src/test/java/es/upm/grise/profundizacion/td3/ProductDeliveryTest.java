package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;

	//@Test
	//public void testDatabaseError() throws DatabaseProblemException {
	//	DatabaseAccessImpl mockDbAccess = Mockito.spy(DatabaseAccessImpl.class);
	//
    //    try {
    //        Mockito.when(mockDbAccess.executeQuery(Mockito.anyString())).thenThrow(SQLException.class);
	//
    //        assertThrows(DatabaseProblemException.class, () -> {
    //            new ProductDelivery(mockDbAccess);
    //        });
    //    } catch (SQLException e) {
    //        // Handle the exception or fail the test if this exception is not expected
    //        fail("Unexpected SQLException thrown in test");
    //    }
	//}

	@Test
	public void testFirstPath() throws MissingOrdersException {
		Vector<Order> mockedOrders = spy (new Vector<Order>());

		try {
			productDelivery = new ProductDelivery();
		} catch (DatabaseProblemException e) {
			fail("Unexpected DatabaseProblemException");
		}

		doReturn(true).when(mockedOrders).isEmpty();
		productDelivery.orders = mockedOrders;
		assertThrows(MissingOrdersException.class, () -> {
                productDelivery.calculateHandlingAmount();
            });
	}

}
