package mainapp;

import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class AppData {


    private static AppData instance;

    private ObservableList<Project> projItems;
    private ObservableList<Task> taskItems;
    private ObservableList<User> userItems;

    private AppData(){
        projItems = FXCollections.observableArrayList();
        taskItems = FXCollections.observableArrayList();
        userItems = FXCollections.observableArrayList();
    }
    public void populate(){
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();
            while(rs.next()){
                projItems.add(new Project(rs.getInt("proj_id"),
                        rs.getString("proj_name"),
                        rs.getString("proj_desc")));
            }
            stmt = SynkConnection.con.prepareStatement("SELECT * FROM users");
            rs = stmt.executeQuery();
            while(rs.next()){
                userItems.add(new User(rs.getInt("user_id"),
                        rs.getString("username")));
            }
            stmt = SynkConnection.con.prepareStatement("SELECT * FROM tasks");
            rs = stmt.executeQuery();

            //    public Task(int taskID, String taskDesc, String taskName, int projID) {
            while(rs.next()){
                taskItems.add(new Task(rs.getInt("task_id"),
                        rs.getString("task_desc"),
                        rs.getString("task_name"),
                        rs.getInt("proj_id")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static AppData getInstance(){
        if(instance == null){
            instance = new AppData();
        }
        return instance;
    }
    public ObservableList<Project> getProjItems() {
        return projItems;
    }

    public ObservableList<Task> getTaskItems() {
        return taskItems;
    }

    public ObservableList<User> getUserItems() {
        return userItems;
    }
}
