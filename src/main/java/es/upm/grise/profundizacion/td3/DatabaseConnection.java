package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {
    Connection getConnection() throws SQLException;
}
