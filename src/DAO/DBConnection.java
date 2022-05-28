package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Mangages the Database Connection */
public class DBConnection {
    /** Database Name */
    private static final String databaseName = "client_schedule";
    /** Database URL */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + databaseName;
    /** Database driver */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /** Database username */
    private static final String username = "sqlUser";
    /** Database Password */
    private static final String password = "Passw0rd!";
    /** Connection */
    static Connection conn;

    /**
     * Opens the database connection.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void openConnection() throws ClassNotFoundException, SQLException, Exception{
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
    }

    /**
     * Closes the database connection.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn.close();
    }
}
