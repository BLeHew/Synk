package connection;

import java.sql.*;


public class SynkConnection {
    public static String url = "jdbc:mysql://localhost:3306/synk?allowPublicKeyRetrieval=true&useSSL=false";

    private static String userName = "root";
    private static String password = "root";
    public static Connection con;
    public static String lastError = "";
    public static boolean hasConnection = false;

    public static void establish(){

        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Successful Connection");
            hasConnection = true;
        } catch (SQLException e){
            System.err.println("Error in DB connection");
        }
    }
    public static boolean hasConnection(){
        try {
            return con.isValid(500);
        }catch (SQLException s){
            System.err.println("Error in checking if connection is valid");
            System.err.println(s);
            return false;
        }
    }
    public static boolean validateCredentials(String userName,String password){
        ResultSet rs;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT username,pass_hash FROM users WHERE username = ?");
            stmt.setString(1,userName);
            rs = stmt.executeQuery();
            if(!rs.first()){
                lastError = "Username does not exist";
                return false;
            }
            if(password.hashCode() != rs.getInt("pass_hash")){
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
        String query = "SELECT username FROM users WHERE username = '" + userName + "'";
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            if(!rs.first()){
                query = "INSERT INTO users VALUES (null,'" + userName + "','" + email + "'," + password.hashCode() + ",1)";
                con.createStatement().executeUpdate(query);
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            System.err.println("Exception in registering credentials.");
            e.printStackTrace();
            System.out.println(query);

            return false;
        }
    }
}
