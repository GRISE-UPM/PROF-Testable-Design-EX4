package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnection {
	 Connection getConnection() throws SQLException;
}
