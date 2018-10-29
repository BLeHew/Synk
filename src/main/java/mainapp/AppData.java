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
    public void addBlankTask(){
        Task task = new Task(0,"No Description","New Task",MainAppUIController.selectedProjectId);
        System.out.println(MainAppUIController.selectedProjectId);
        //taskItems.add(task);
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("INSERT INTO tasks VALUES(null,?,?,?)");
            stmt.setInt(1,task.getProjID());
            stmt.setString(2,task.getName());
            stmt.setString(3,task.getDesc());
            stmt.executeUpdate();

            Statement s = SynkConnection.con.createStatement();
            ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()){
                task.setId(rs.getInt("LAST_INSERT_ID()"));
            }
            taskItems.add(task);

        }catch (SQLException s) {
            s.printStackTrace();
        }
    }
    public void populateData(){
        ResultSet rs;
        try {

            //PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = SynkConnection.getTableItems(new Project());
            while (rs.next()) {
                projItems.add(new Project(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")));
            }
            rs = SynkConnection.getTableItems(new Task());
            while(rs.next()){
                taskItems.add(new Task(rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("name"),
                        rs.getInt("proj_id")));
            }
            rs = SynkConnection.getTableItems(new User());
            while (rs.next()) {
                userItems.add(new User(rs.getInt("id"),
                        rs.getString("username")));
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
    }
    public void remove(Task task){
        taskItems.remove(task);
        try {
            SynkConnection.con.createStatement().executeUpdate("DELETE FROM tasks WHERE id = " + task.getId());
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
    public void updateDatabase(String type,int id,String newValue){
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("UPDATE " + type + " SET name = ? WHERE id = " + id);
            stmt.setString(1,newValue);
            System.out.println(stmt);
            stmt.executeUpdate();
        }catch (SQLException s){

            s.printStackTrace();
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
