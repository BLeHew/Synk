package connection;

import java.sql.*;


public class SynkConnection {
    public static String url = "jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true&useSSL=false";
    public static String driver = "com.mysql.jdbc.Driver";
    public static Connection con;
    public static String lastError = "";
    public static boolean hasConnection = false;
    private static String userName = "root";
    private static String password = "root";

    public static void establish() {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Error in loading driver " + e);
        }
        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Successful Connection");
            hasConnection = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasConnection() {
        try {
            return con.isValid(500);
        } catch (SQLException s) {
            System.err.println("Error in checking if connection is valid");
            System.err.println(s);
            return false;
        }
    }

    public static boolean validateCredentials(String userName, String password) {
        ResultSet rs;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT USERNAME,PASS_HASH FROM users WHERE USERNAME = ?");
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (!rs.first()) {
                lastError = "Username does not exist";
                return false;
            }
            if (password.hashCode() != rs.getInt("PASS_HASH")) {
                lastError = "Password Incorrect";
                return false;
            }
        } catch (SQLException e) {
            lastError = "Error in SQL Connection";
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean registerCredentials(String userName, String password, String email) {
        String query = "SELECT USERNAME FROM users WHERE USERNAME = '" + userName + "'";
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            if (!rs.first()) {
                query = "INSERT INTO users VALUES (null," + userName + "," + email + "," + password.hashCode() + ",1)";
                con.createStatement().executeUpdate(query);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Exception in registering credentials.");
            System.err.println(e);
            System.out.println(query);

            return false;
        }
    }
}
