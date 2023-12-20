package es.upm.grise.profundizacion.td3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminDriver {

    // Metodo para realizar la conexion a la base de datos y poder manipularlo con mockito
    public Connection getConnection(String string) throws SQLException, DatabaseProblemException {
        return DriverManager.getConnection(string);
    }
    
}
