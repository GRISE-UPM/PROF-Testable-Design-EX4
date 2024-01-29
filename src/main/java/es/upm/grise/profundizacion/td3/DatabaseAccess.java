package es.upm.grise.profundizacion.td3;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseAccess {
    ResultSet executeQuery(String query) throws SQLException;
}