package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import tableobjects.*;

import java.sql.*;


public class DBSource {
    public static String url = "jdbc:mysql://localhost:3306/synk?allowPublicKeyRetrieval=true&useSSL=false";
    private static String userName = "root";
    private static String password = "root";
    public static String lastError = "";
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
        if(con.isRunning()){
            System.out.println("Connection made");
        }
    }
    public static Connection getConnection() throws SQLException{
        return con.getConnection();
    }
    public static boolean insertAssignment(int userId,int projId,int taskId){
        Connection conn = null;
        try {
            conn = con.getConnection();
            if (taskId != -1) {
                conn.prepareStatement("INSERT IGNORE INTO user_task_assigned VALUES(" + userId + "," + taskId + ")")
                        .executeUpdate();
            }
            conn.prepareStatement("INSERT IGNORE INTO user_proj_assigned VALUES(" + userId + "," + projId + ")")
                    .executeUpdate();
            return true;
        }catch (SQLException s){
            s.printStackTrace();
        }finally { close(conn); }
        return false;
    }
    public static ObservableList<TableObject> getItems(String type, String query){
        ObservableList<TableObject> o = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = con.getConnection();
            ResultSet rs = conn.prepareStatement(query).executeQuery();
            while (rs.next()){
                o.add(TableObjectFactory.getTableObject(type,rs));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }finally { close(conn); }
        return o;
    }
    public static void close(Connection c){
        try { if (c != null){ c.close(); } } catch (SQLException s) { s.printStackTrace(); }
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
        }finally {close(conn); }
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
        } finally {close(conn); }
    }

}
