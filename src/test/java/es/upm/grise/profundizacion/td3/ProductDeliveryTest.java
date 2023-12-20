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
	public void chequearDBvacia() throws MissingOrdersException  {
		orders = spy (new Vector<Order>());
		// En caso de caer en la primera condicion de isEmpty(), que devuelva true. Por eso el
		doReturn(true).when(orders).isEmpty();
		assertThrows(MissingOrdersException.class, () -> { 
			productDelivery.orders = orders; 
			productDelivery.calculateHandlingAmount();
		});
	}


}
