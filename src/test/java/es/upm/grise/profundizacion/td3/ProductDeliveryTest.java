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
	// Segundo camino: Realiza el bucle  y el número de pedidos es mayor 10
	public void testC2() throws Exception {
		// Crear un mock de la conexión
		Connection mockConnection = Mockito.mock(Connection.class);
		Statement mockStatement = Mockito.mock(Statement.class);
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);

		// Configurar los mocks para simular el comportamiento deseado
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);
		// Simular que hay 11 pedidos en la base de datos
		Mockito.when(mockResultSet.next()).thenReturn(true, true, true, true, true, true, true, true, true, true, true, false);
		Mockito.when(mockResultSet.getDouble("amount")).thenReturn(100.0);
		// Crear la instancia de ProductDelivery con la conexión mockeada
		ProductDelivery productDelivery = new ProductDelivery(mockConnection);

		// Ejecutar el método calculateHandlingAmount y verificar que no se lanza MissingOrdersException
		// Hay más de 10 pedidos, por lo tanto 3% del importe total de los pedidos
		// Hay 11 pedidos con un importe de 100, por lo tanto el importe total es 1100
		// Al importe total se le aplica el 3% -> 1100 * 0.03 = 33
		double resultadoEsperado = 33.0;
		assertEquals(resultadoEsperado, productDelivery.calculateHandlingAmount());

	}

	@Test
	// Tercer camino: Realiza el bucle  y el número de pedidos es menor 10
	public void testC3() throws Exception {
	    // Crear un mock de la conexión
		Connection mockConnection = Mockito.mock(Connection.class);
		Statement mockStatement = Mockito.mock(Statement.class);
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
	
		// Configurar los mocks para simular el comportamiento deseado
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);
		// Simular que hay 3 pedidos en la base de datos
		Mockito.when(mockResultSet.next()).thenReturn(true, true, true, false);
		Mockito.when(mockResultSet.getDouble("amount")).thenReturn(100.0);
		// Crear la instancia de ProductDelivery con la conexión mockeada
		ProductDelivery productDelivery = new ProductDelivery(mockConnection);
	
		// Ejecutar el método calculateHandlingAmount y verificar que no se lanza MissingOrdersException
		// Hay menos de 10 pedidos, por lo tanto 2% del importe total de los pedidos
		// Hay 3 pedidos con un importe de 100, por lo tanto el importe total es 300
		// Al importe total se le aplica el 5% -> 300 * 0.02 = 6
		double resultadoEsperado = 6.0;
		assertEquals(resultadoEsperado, productDelivery.calculateHandlingAmount());
	}



}