package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tableobjects.*;

import java.sql.*;


public class SynkConnection {
    public static String url = "jdbc:mysql://localhost:3306/synk?allowPublicKeyRetrieval=true&useSSL=false";

    private static String userName = "root";
    private static String password = "root";
    public static String lastError = "";
    public static boolean hasConnection = false;

    public static HikariDataSource con;

    public static void establish(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        con = new HikariDataSource(config);
    }
    public static boolean validateCredentials(String userName,String password){
        ResultSet rs;
        PreparedStatement stmt;
        Connection conn = null;
        try {
            conn = con.getConnection();
            stmt = conn.prepareStatement("SELECT username,pass_hash FROM users WHERE username = ?");
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
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
        return true;
    }
    public static boolean registerCredentials(String userName, String password,String email){
        String query = "SELECT username FROM users WHERE username = '" + userName + "'";
        Connection conn = null;
        try {
            conn = con.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(query);
            if(!rs.first()){
                query = "INSERT INTO users VALUES (null,'" + userName + "','" + email + "'," + password.hashCode() + ",1)";
                con.getConnection().createStatement().executeUpdate(query);
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            System.err.println("Exception in registering credentials.");
            e.printStackTrace();
            System.out.println(query);
            return false;
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
    }
}
