package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomDriverManager{
    
    public Connection getConnection(String url) throws SQLException, DatabaseProblemException {
        return DriverManager.getConnection(url);
    }
}
