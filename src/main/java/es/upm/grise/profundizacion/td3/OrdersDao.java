package es.upm.grise.profundizacion.td3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * This class handles the retrieving of Orders from the database.
 * <p>
 * This class does not close the provided connection, so it must be responsability of the
 * supplier to close it
 */
public class OrdersDao {
    private final Connection connection;

    public OrdersDao(Connection connection) {
        this.connection = connection;
    }

    public Vector<Order> getOrders() throws DatabaseProblemException {
        try {

            // Read from the orders table
            String query = "SELECT * FROM orders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Vector<Order> orders = new Vector<>();

            // Iterate until we get all orders' data
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                double amount = resultSet.getDouble("amount");
                orders.add(new Order(id, amount));

            }

            return orders;

        } catch (Exception e) {

            throw new DatabaseProblemException();

        }
    }
}
