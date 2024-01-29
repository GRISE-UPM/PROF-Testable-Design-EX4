package es.upm.grise.profundizacion.td3;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccessImpl implements DatabaseAccess {
    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:resources/orders.db");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
