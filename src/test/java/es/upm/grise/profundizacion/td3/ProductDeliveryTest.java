package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProductDeliveryTest {

	ProductDelivery productDelivery;
	CustomDriverManager driver;

	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		driver = mock(CustomDriverManager.class);
	}

	@Test
	public void test() throws MissingOrdersException {
		assertEquals(20, productDelivery.calculateHandlingAmount());
	}

	@Test
	public void dataBaseProblemException() throws DatabaseProblemException, SQLException {
		when(driver.getConnection(anyString())).thenThrow(new DatabaseProblemException());

		assertThrows(DatabaseProblemException.class, () -> {
			new ProductDelivery(driver);
		});
	}

	@Test
	public void missingOrdersException() throws MissingOrdersException {
		Vector<Order> orders = new Vector<Order>();
		Order order = mock(Order.class);
		orders.add(order);
		productDelivery.setOrders(orders);

		assertEquals(productDelivery.getOrders().size(), 1);
	}


	@Test
	public void testCalculateHandlingAmount() throws MissingOrdersException {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		Order order1 = mock(Order.class);
		Order order2 = mock(Order.class);
		orders.add(order1);
		orders.add(order2);
		productDelivery.setOrders(orders);
		double expectedHandlingAmount = (order1.getAmount() + order2.getAmount()) * SystemConfiguration.getInstance().getHandlingPercentage();

		// Act
		double actualHandlingAmount = productDelivery.calculateHandlingAmount();

		// Assert
		assertEquals(expectedHandlingAmount, actualHandlingAmount);
	}

	@Test
	public void testCalculateHandlingAmount_NoOrders() {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		productDelivery.setOrders(orders);

		// Assert
		assertThrows(MissingOrdersException.class, () -> {
			// Act
			productDelivery.calculateHandlingAmount();
		});
	}

	@Test
	public void testCalculateHandlingAmount_LateHourAndManyOrders() throws MissingOrdersException {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		for (int i = 0; i < 11; i++) {
			Order order = mock(Order.class);
			orders.add(order);
		}
		productDelivery.setOrders(orders);
		double expectedHandlingAmount = 0.01 + (orders.size() * SystemConfiguration.getInstance().getHandlingPercentage());

		// Act
		double actualHandlingAmount = productDelivery.calculateHandlingAmount();

		// Assert
		assertEquals(expectedHandlingAmount, actualHandlingAmount);
	}

	@Test
	public void testCalculateHandlingAmount_EarlyHourAndFewOrders() throws MissingOrdersException {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		Order order = mock(Order.class);
		orders.add(order);
		productDelivery.setOrders(orders);
		double expectedHandlingAmount = order.getAmount() * SystemConfiguration.getInstance().getHandlingPercentage();

		// Act
		double actualHandlingAmount = productDelivery.calculateHandlingAmount();

		// Assert
		assertEquals(expectedHandlingAmount, actualHandlingAmount);
	}

	@Test
	public void testCalculateHandlingAmount_EarlyHourAndManyOrders() throws MissingOrdersException {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		for (int i = 0; i < 11; i++) {
			Order order = mock(Order.class);
			orders.add(order);
		}
		productDelivery.setOrders(orders);
		double expectedHandlingAmount = orders.size() * SystemConfiguration.getInstance().getHandlingPercentage();

		// Act
		double actualHandlingAmount = productDelivery.calculateHandlingAmount();

		// Assert
		assertEquals(expectedHandlingAmount, actualHandlingAmount);
	}

	@Test
	public void testCalculateHandlingAmount_LateHourAndFewOrders() throws MissingOrdersException {
		// Arrange
		Vector<Order> orders = new Vector<Order>();
		Order order = mock(Order.class);
		orders.add(order);
		productDelivery.setOrders(orders);
		double expectedHandlingAmount = 0.01 + (order.getAmount() * SystemConfiguration.getInstance().getHandlingPercentage());

		// Act
		double actualHandlingAmount = productDelivery.calculateHandlingAmount();

		// Assert
		assertEquals(expectedHandlingAmount, actualHandlingAmount);
	}



}
