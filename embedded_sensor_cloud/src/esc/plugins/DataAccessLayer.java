package esc.plugins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author Alex
 */
public class DataAccessLayer {
    private static DataAccessLayer ourInstance = new DataAccessLayer();

    private Connection connection;
    private ResultSet resultSet;

    public static DataAccessLayer getInstance() {
        return ourInstance;
    }

    private DataAccessLayer() {
        try
        {
            if (connection != null) connection.close();

            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://ALEX-NOTEBOOK;databaseName=ErpData";
            connection =  DriverManager.getConnection(connectionUrl, "test_user", "password_yo");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
