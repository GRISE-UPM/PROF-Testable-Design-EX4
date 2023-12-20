package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	ConnectionDB connectionDB;
	Vector<Order> orders ;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		
		productDelivery = spy(new ProductDelivery());
	}
	

	// agregamos una clase ConnectionDB para poder mockearla y asi pedirle que lance la execpcion por no tener una URL VALIDA
	@Test
	public void bbddExiste() throws  DatabaseProblemException  {
		connectionDB = mock(ConnectionDB.class);
		when(connectionDB.getConnection(anyString())).thenThrow(new DatabaseProblemException());
	}


	@Test
	// Comprueba que se tire la excepcion
	public void chequearOrdersVacio() throws MissingOrdersException  {
		orders = spy (new Vector<Order>());
		// En caso de caer en la primera condicion de isEmpty(), que devuelva true. Por eso el
		doReturn(true).when(orders).isEmpty();
		assertThrows(MissingOrdersException.class, () -> { 
			productDelivery.orders = orders; 
			productDelivery.calculateHandlingAmount();
		});
	}


	@Test
	// Comprueba que se tire la excepcion
	public void hourMayor22() throws MissingOrdersException  {

		int entraEnIf = 30;
		orders = spy (new Vector<Order>());
		// En caso de caer en la primera condicion de isEmpty(), que devuelva true. Por eso el
		doReturn(24).when(productDelivery).obtainHour();
		assertEquals(entraEnIf , productDelivery.calculateHandlingAmount());
		
	}
	@Test
	public void hourMenor22OrderMayor10() throws MissingOrdersException  {
		int entraEnIf = 30;
		orders = spy (new Vector<Order>());
		// En caso de caer en la primera condicion de isEmpty(), que devuelva true. Por eso el
		doReturn(15).when(productDelivery).obtainHour();
		doReturn(25).when(productDelivery).getOrdersTam();
		assertEquals(entraEnIf , productDelivery.calculateHandlingAmount());

		
	}

	@Test
	public void hourMenor22OrderMenor10() throws MissingOrdersException  {
		int noEntraIf = 20;
		orders = spy (new Vector<Order>());
		// En caso de caer en la primera condicion de isEmpty(), que devuelva true. Por eso el
		doReturn(15).when(productDelivery).obtainHour();
		doReturn(4).when(productDelivery).getOrdersTam();
		assertEquals(noEntraIf , productDelivery.calculateHandlingAmount());

		
	}


	


}
