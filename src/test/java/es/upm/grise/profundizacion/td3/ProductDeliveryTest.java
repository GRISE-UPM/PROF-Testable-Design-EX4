package es.upm.grise.profundizacion.td3;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductDeliveryTest {
	
	ProductDelivery productDelivery;
	
	@BeforeEach
	public void setUp() throws DatabaseProblemException {
		//productDelivery = new ProductDelivery();
	}

	@Test 
	public void test1() throws DatabaseProblemException, SQLException{
		Connection mockConnection = Mockito.mock(Connection.class);

		// Comportamiento del mock
		Mockito.when(mockConnection.createStatement()).thenThrow(new SQLException());
			
		// Comprobaciones
		assertThrows(DatabaseProblemException.class, () -> new ProductDelivery(mockConnection));
		verify(mockConnection,times(1)).createStatement();

	}

 
	@Test
	// Primer camino: Se lanza la excepción MissingOrdersThrowsException()
	public void testC1() throws Exception {
		// Crear un mock de la conexión
		Connection mockConnection = Mockito.mock(Connection.class);
		Statement mockStatement = Mockito.mock(Statement.class);
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
	
		// Configurar los mocks para simular el comportamiento deseado
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(false);
	
		// Crear la instancia de ProductDelivery con la conexión mockeada
		ProductDelivery productDelivery = new ProductDelivery(mockConnection);
	
		// Verificar que se lanza MissingOrdersException
		assertThrows(MissingOrdersException.class, () -> productDelivery.calculateHandlingAmount());
	}

	@Test
	// Segundo camino: 
	public void testC2() {


	}



}