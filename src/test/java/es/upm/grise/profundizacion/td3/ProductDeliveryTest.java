package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ProductDeliveryTest {

    ProductDelivery productDelivery;

    @Mock
    Connection connection;

    private AutoCloseable mocksClose;


    @BeforeEach
    public void setUp() throws DatabaseProblemException {
        mocksClose = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mocksClose.close();
    }

    @Test
    public void test() throws MissingOrdersException {
        assertEquals(20, productDelivery.calculateHandlingAmount());
    }

    @Test
    public void testErrorIsThrownOnDatabaseError() throws SQLException {
        SQLException fakeSqlException = mock();
        when(connection.createStatement()).thenThrow(fakeSqlException);

        Assertions.assertThrows(DatabaseProblemException.class,() -> {
            productDelivery = new ProductDelivery(connection);
        });
    }

}
