package es.upm.grise.profundizacion.td3;

import java.util.Vector;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/* AGREGAMOS ESTA CLASE CON LA FINALIDAD DE PODER PEDIR QUE LANCE LA EXCEPCION AL MOCKEAR */ 


public class ConnectionDB {
    
    public Connection getConnection(String url) throws DatabaseProblemException {

        try{

            return DriverManager.getConnection(url);

        } catch (SQLException e){

            throw new DatabaseProblemException();

        }
    }
}
