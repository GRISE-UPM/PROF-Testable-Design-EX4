package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerPersonal {
	public Connection getConnection(String db) throws SQLException, DatabaseProblemException
	{
		
		return DriverManager.getConnection(db);
	}
}
