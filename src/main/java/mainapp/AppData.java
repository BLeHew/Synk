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
    public void populateData(){
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();
            while (rs.next()) {
                projItems.add(new Project(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")));
            }
            stmt = SynkConnection.con.prepareStatement("SELECT * FROM tasks");
            rs = stmt.executeQuery();
            while(rs.next()){
                taskItems.add(new Task(rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("name"),
                        rs.getInt("proj_id")));
            }
            stmt = SynkConnection.con.prepareStatement("SELECT * FROM users");
            rs = stmt.executeQuery();
            while (rs.next()) {
                userItems.add(new User(rs.getInt("id"),
                        rs.getString("username")));
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
    }
    public void updateDatabase(String type,int id,String newValue){
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("UPDATE "+ type + " SET name = ? WHERE id = ?");
            stmt.setString(1,newValue);
            stmt.setInt(2,id);
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
