package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Vector;

import static org.mockito.Mockito.*;

public class ProductDeliveryTest {

    private static final Order order1 = new Order(1, 5.0);
    private static final Order order2 = new Order(2, 1.0);

    ProductDelivery productDelivery;

    @Mock
    OrdersDao ordersDao;

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
    public void testErrorIsThrownOnDatabaseError() throws DatabaseProblemException {
        DatabaseProblemException fakeSqlException = mock();
        when(ordersDao.getOrders()).thenThrow(fakeSqlException);

        Assertions.assertThrows(DatabaseProblemException.class, () -> {
            productDelivery = new ProductDelivery(ordersDao);
        });
    }

    @Nested
    class ListErrors {
        // We will use this as a mutable vector of orders by passing it reference
        // through the mock
        private Vector<Order> orders;

        private Clock clock;

        @BeforeEach
        void setUp() throws DatabaseProblemException {
            orders = new Vector<>();
            when(ordersDao.getOrders()).thenReturn(orders);

        }

        @Test
        void testEmptyList() throws DatabaseProblemException {
            productDelivery = new ProductDelivery(ordersDao);
            Assertions.assertThrows(MissingOrdersException.class, () -> {
                productDelivery.calculateHandlingAmount();
            });
        }

        @Test
        void testListWithContentBefore22() throws DatabaseProblemException, MissingOrdersException {
            Instant instant = Instant.parse("2023-12-20T10:00:00Z");
            Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
            productDelivery = new ProductDelivery(ordersDao, clock);

            orders.add(order1);
            orders.add(order2);

            double result = productDelivery.calculateHandlingAmount();
            double expecting = (order1.amount + order2.amount) * 0.02;
            Assertions.assertEquals(expecting, result);
        }

        @Test
        void testListWithContentAfter22() throws DatabaseProblemException, MissingOrdersException {
            Instant instant = Instant.parse("2023-12-20T22:00:00Z");
            Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
            productDelivery = new ProductDelivery(ordersDao, clock);

            orders.add(order1);
            orders.add(order2);

            double result = productDelivery.calculateHandlingAmount();
            double expecting = (order1.amount + order2.amount) * 0.03;
            Assertions.assertEquals(expecting, result);
        }


        @Test
        void testListWithContentBefore22AndMoreThan10Items() throws DatabaseProblemException, MissingOrdersException {
            Instant instant = Instant.parse("2023-12-20T10:00:00Z");
            Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
            productDelivery = new ProductDelivery(ordersDao, clock);

            int nItems = 11;

            for (int i = 0; i < nItems; i++) {
                orders.add(order1);
            }

            double result = productDelivery.calculateHandlingAmount();
            double expecting = (order1.amount * nItems) * 0.03;
            Assertions.assertEquals(expecting, result);
        }


        @Test
        void testListWithContentAfter22AndMoreThan10Items() throws DatabaseProblemException, MissingOrdersException {
            Instant instant = Instant.parse("2023-12-20T10:00:00Z");
            Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
            productDelivery = new ProductDelivery(ordersDao, clock);

            int nItems = 11;

            for (int i = 0; i < nItems; i++) {
                orders.add(order1);
            }

            double result = productDelivery.calculateHandlingAmount();
            double expecting = (order1.amount * nItems) * 0.03;
            Assertions.assertEquals(expecting, result);
        }

    }

}
