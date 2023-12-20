package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	ConnectionDB connectionDB;
	
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


}
