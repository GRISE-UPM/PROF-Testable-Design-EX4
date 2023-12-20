package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class DataBaseError { //Clase para poder generar DatabaseProblemException
    Connection connection;
    private String database;

    public DataBaseError(String database) throws DatabaseProblemException{
        this.database=database;
    }

    public Vector<Order> dataBaseCreated() throws DatabaseProblemException {
        try {
            Vector<Order> orders=new Vector<Order>();
            // Create DB connection
            connection = DriverManager.getConnection(database);

            // Read from the orders table
            String query = "SELECT * FROM orders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate until we get all orders' data
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                double amount = resultSet.getDouble("amount");
                orders.add(new Order(id, amount));

            }
            // Close the connection
            connection.close();
            return orders;

        } catch (Exception e) {

            throw new DatabaseProblemException();

        }
    }
}
