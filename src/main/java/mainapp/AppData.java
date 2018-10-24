package mainapp;

import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class AppData {

    private static ObservableList<Project> projItems;
    private static ObservableList<Task> taskItems;
    private static ObservableList<User> userItems;

    public static ObservableList<Project> getProjItems() {
        projItems = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();
            while (rs.next()) {
                projItems.add(new Project(rs.getInt("proj_id"),
                        rs.getString("proj_name"),
                        rs.getString("proj_desc")));
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        return projItems;
    }

    public static ObservableList<Task> getTaskItems() {
        taskItems = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM tasks");
            rs = stmt.executeQuery();
            while(rs.next()){
                taskItems.add(new Task(rs.getInt("task_id"),
                        rs.getString("task_desc"),
                        rs.getString("task_name"),
                        rs.getInt("proj_id")));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return taskItems;
    }

    public static ObservableList<User> getUserItems() {
        userItems = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM users");
            rs = stmt.executeQuery();
            while (rs.next()) {
                userItems.add(new User(rs.getInt("user_id"),
                        rs.getString("username")));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return userItems;
    }
}
