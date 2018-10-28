package mainapp;

import java.sql.*;

import connection.*;
import javafx.collections.*;
import tableobjects.*;

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
            PreparedStatement stmt = SynkConnection.con.prepareStatement("UPDATE ? SET name = ? WHERE id = ?");
            stmt.setString(1,type);
            stmt.setString(2,newValue);
            stmt.setInt(3,id);
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
