package es.upm.grise.profundizacion.td3;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//Se crea esta clase para poder pasarsela como un mock a ProductDelivery
public class DBSource {

    private String databaseUrl;

    public DBSource(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public Vector<Order> fetchOrders() throws DatabaseProblemException {
        Vector<Order> orders = new Vector<>();

        try {

            // Create DB connection
            Connection connection = DriverManager.getConnection(databaseUrl);

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

        } catch (Exception e) {

            throw new DatabaseProblemException();

        }

        return orders;
    }
}

