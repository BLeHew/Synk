package mainapp;

import java.sql.*;

import connection.*;
import javafx.collections.*;
import tableobjects.*;

import javax.xml.transform.Result;

public class AppData {

    private static ObservableList<Project> projItems;
    private static ObservableList<Task> taskItems;
    private static ObservableList<User> userItems;

    private static int lastProjectID = 0;
    private static int lastTaskID = 0;

    private AppData(){
        projItems = FXCollections.observableArrayList();
        taskItems = FXCollections.observableArrayList();
        userItems = FXCollections.observableArrayList();
    }
    private static AppData instance;
    public static AppData getInstance(){
        if (instance == null){
            instance = new AppData();
        }
        return instance;
    }
    public void addBlankTask(int projId){
        Task task = new Task(projId);
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO tasks VALUES(null,?,?,?)");
            stmt.setInt(1,task.getProjID());
            stmt.setString(2,task.getName());
            stmt.setString(3,task.getDesc());
            System.out.println(stmt);
            stmt.executeUpdate();

            Statement s = SynkConnection.con.getConnection().createStatement();
            ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()){
                task.setId(rs.getInt("LAST_INSERT_ID()"));
            }
            taskItems.add(task);

        }catch (SQLException s) {
            s.printStackTrace();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
    }
    public void populateData(){
        ResultSet rs;
        PreparedStatement stmt;
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();

            while(rs.next()){
                projItems.add(new Project(rs));
            }
            rs = stmt.executeQuery("SELECT * FROM tasks");
            while(rs.next()){
                taskItems.add(new Task(rs));
            }
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                userItems.add(new User(rs));
            }
        }catch(SQLException s){
            s.printStackTrace();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
    }
    public void remove(Task task){
        taskItems.remove(task);
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            conn.prepareStatement("DELETE FROM tasks WHERE id = " + task.getId()).executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
    }
    public void updateDatabase(String type,int id,String newValue){
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + type + " SET name = ? WHERE id = " + id);
            stmt.setString(1,newValue);
            System.out.println(stmt);
            stmt.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
    }
    public ObservableList<Project> getProjItems() {
        return projItems;
    }

    public ObservableList<Task> getTaskItems() {
        return taskItems;
    }

    public  ObservableList<User> getUserItems() {
        return userItems;
    }
}
