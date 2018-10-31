package mainapp;

import connection.Query;
import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        SynkConnection.insertNewTask(task);
        task.setId(SynkConnection.getLastInsertId());
        taskItems.add(task);
    }
    public void populateData(){
        SynkConnection.addItemsToList(projItems, "projects");
        SynkConnection.addItemsToList(taskItems,"tasks");
        SynkConnection.addItemsToList(userItems,"users");
    }

    public void remove(Task task){
        taskItems.remove(task);
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            conn.prepareStatement("DELETE FROM tasks WHERE id = " + task.getId()).executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {SynkConnection.close(conn);}
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
