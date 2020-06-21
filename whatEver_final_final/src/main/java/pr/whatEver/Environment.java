package pr.whatEver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class Environment {
    public static Connection con;
    public static Random random = new Random();
    private static boolean connected = false;
    public static CallableStatement storeProcedure;

    /**
     * Connect to database
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection CreateConnection() throws ClassNotFoundException, SQLException {
        if (!connected) {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/whatever?autoReconnect=true&useSSL=false",
                            "root", "Minhtri27");
            connected = true;
        }
        return con;
    }
}
