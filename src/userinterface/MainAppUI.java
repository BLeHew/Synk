package userinterface;

import TableObjects.Task;
import TableObjects.User;
import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import TableObjects.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainAppUI {
    ObservableList<Project> projItems = FXCollections.observableArrayList();
    ObservableList<Task> taskItems = FXCollections.observableArrayList();
    ObservableList<User> userItems = FXCollections.observableArrayList();

    @FXML private ListView<Project> listViewProjects;
    @FXML private ListView<Task> listViewTasks;
    @FXML private ListView<User> listViewUsers;

    //TODO move some functionality to a separate class, maybe a UI controller of some sorts
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    public void initialize(){
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();
            while(rs.next()){
                projItems.add(new Project(rs.getInt("PROJ_ID"),
                        rs.getString("PROJ_NAME"),
                        rs.getString("PROJ_DESC")));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        listViewProjects.setItems(projItems);
    }
    @FXML
    public void narrowUsers(){
        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        int taskID = listViewTasks.getItems().get(listViewTasks.getSelectionModel().getSelectedIndex()).getTaskID();

        userItems.clear();
        try {
            ResultSet rs;
            PreparedStatement stmt = SynkConnection.con.prepareStatement(
                    "SELECT u.USER_ID,u.USERNAME,a.TASK_ID\n" +
                            " FROM users u,assignedto a\n" +
                            " WHERE u.USER_ID = a.USER_ID AND a.PROJ_ID = " + listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId() +
                            " AND a.TASK_ID = " + taskID);
            rs = stmt.executeQuery();
            while(rs.next()){
                userItems.add(new User(rs.getInt("USER_ID"), rs.getString("USERNAME")));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        listViewUsers.setItems(userItems);
    }
    @FXML
    public void showProjectTasksAndUsers(){
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        taskItems.clear();
        userItems.clear();
        //listViewUsers.refresh();
        //listViewTasks.refresh();

        ResultSet rs;
        int projId = listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId();
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM tasks WHERE Proj_ID =" + projId);
            rs = stmt.executeQuery();
            while(rs.next()){
                taskItems.add(new Task(
                        rs.getInt("TASK_ID"),
                        rs.getString("TASK_DESC"),
                        rs.getString("TASK_NAME"),
                        rs.getInt("PROJ_ID")));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement(
                    "SELECT * FROM users u " +
                            "inner join assignedto a " +
                            "ON a.USER_ID = u.USER_ID " +
                            "inner join projects p on p.PROJ_ID =" +  projId
                            + " GROUP BY u.USER_ID;");
            rs = stmt.executeQuery();
            while(rs.next()){
                userItems.add(new User(rs.getInt("USER_ID"), rs.getString("USERNAME")));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        listViewTasks.setItems(taskItems);
        listViewUsers.setItems(userItems);
    }

}
