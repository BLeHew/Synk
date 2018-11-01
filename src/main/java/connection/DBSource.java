package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableobjects.Project;
import tableobjects.TableObjectFactory;
import tableobjects.Task;
import tableobjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
    @SuppressWarnings("unchecked")
    public static ObservableList getItems(String type, String query){
        ObservableList o = FXCollections.observableArrayList();
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
    public static Object insertItem(Object item){
        Connection conn = null;
        try {
            conn = con.getConnection();
            //PreparedStatement stmt = conn.prepareStatement(Query.insertItem(item));

        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }
    public static Project addBlankProject(){
        Connection conn = null;
        Project p = new Project();
        try {
            conn = con.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO projects VALUES (null,?,?)");
            stmt.setString(1,p.getName());
            stmt.setString(2,p.getDesc());
            stmt.executeUpdate();
            p.setId(getLastInsertId());
        }catch (SQLException s){
            s.printStackTrace();
        }
        return p;
    }
    public static void close(Connection c){
        try { if (c != null){ c.close(); } } catch (SQLException s) { s.printStackTrace(); }
    }
    public static void insertNewTask(Task task){
        Connection conn = null;
        try {
            conn = con.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO tasks VALUES(null,?,?,?,0)");
            stmt.setInt(1,task.getProjID());
            stmt.setString(2,task.getName());
            stmt.setString(3,task.getDesc());
            stmt.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        } finally {close(conn);}
    }
    public static int getLastInsertId(){
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = con.getConnection();
            rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
            rs.next();
            if(rs.next()){
                return rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException s){
            s.printStackTrace();
        }finally {close(conn); }
        return 0;
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
