package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
    public static String driver = "com.mysql.cj.jdbc.Driver";
    public static String userName; // = txtFieldUsername.getText();
    public static String password; // = passwordFieldPassword.getText();
    public static Connection con;

    public static boolean establish(){
        try {
            con = DriverManager.getConnection(url, userName, password);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
