package connection;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SynkConnection {
    public static String url = "jdbc:mysql://localhost:3306/synk?allowPublicKeyRetrieval=true&useSSL=false";
    public static String driver = "com.mysql.jdbc.Driver";
    private static String userName = "root";
    private static String password = "root";
    public static Connection con;
    public static String lastError = "";
    public static boolean hasConnection = false;
    public static void establish(){
        try {
            Class.forName(driver);
        }catch(Exception e){
            System.err.println("Error in loading driver " + e);
        }
        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Successful Connection");
            hasConnection = true;
        } catch (SQLException e){
            System.err.println("Error in DB connection");
        }

    }
    public static boolean validateCredentials(String userName,String password){
        String query = "SELECT USERNAME,PASS_HASH FROM users WHERE USERNAME='" + userName + "'";
        ResultSet rs;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
            if(!rs.first()){
                lastError = "Username does not exist";
                return false;
            }
            if(password.hashCode() != rs.getInt("PASS_HASH")){
                lastError = "Password Incorrect";
                return false;
            }
        }catch(SQLException e){
            lastError = "Error in SQL Connection";
            System.err.println(e);
            return false;
        }
        return true;
    }
    public static boolean registerCredentials(String userName, String password,String email){
        return true;
    }
}
