package lk.ijse.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    private static dbconnection dbConnection;
    private Connection connection;

    private dbconnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ceranova",
                "root",
                "Ijse@1234");
    }
    public static dbconnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new dbconnection();
        }
        return dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }

}
