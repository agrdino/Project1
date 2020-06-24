package pr.whatEver;

import java.sql.*;
import java.util.Random;

public class Environment {
    public static Connection con;
    public static Random random = new Random();
    private static boolean connected = false;
    public static CallableStatement storeProcedure;
    public static Statement stmt;
    public static ResultSet resultSet;
    public static int userID;

    /**
     * Connect to database
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection CreateConnection() throws SQLException {
        try {
            if (!connected) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.
                        getConnection("jdbc:mysql://localhost:3306/whatever?autoReconnect=true&useSSL=false",
                                "root", "Minhtri27");
                connected = true;
                stmt = con.createStatement();
            }
        }
        catch (ClassNotFoundException e)
            {
                System.out.println("Cant connect to database, please check the connection!");
            }
        catch (SQLException e)
        {
            System.out.println("System error!");
        }
        return con;
    }
    public static Statement CreateStatement() throws SQLException {
        if (!connected) {
            stmt = CreateConnection().createStatement();
            connected = true;
        }
        return stmt;
    }
}
