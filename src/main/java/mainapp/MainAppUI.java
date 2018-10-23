package mainapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javafx.collections.transformation.FilteredList;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;
import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import userinterface.SynkApp;

public class MainAppUI {
    @FXML private ListView<Project> listViewProjects;
    @FXML private ListView<Task> listViewTasks;
    @FXML private ListView<User> listViewUsers;

    //TODO move some functionality to a separate class, maybe a UI controller of some sorts
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    @FXML
    public void displayForm() {
        SynkApp.getInstance().showForm("/fxml/makeprojectform.fxml");
    }
    public void initialize(){
        listViewProjects.setItems(AppData.getInstance().getProjItems());
    }
    @FXML
    public void narrowUsers(){

        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        int taskID = listViewTasks.getItems().get(listViewTasks.getSelectionModel().getSelectedIndex()).getTaskID();

        HashSet<Integer> userIdsToDisplay = new HashSet<>();
        try {
            ResultSet rs;
            PreparedStatement stmt = SynkConnection.con.prepareStatement(
                    "SELECT u.USER_ID,u.USERNAME\n" +
                            " FROM users u,user_task_assigned uta\n" +
                            " WHERE u.user_id = uta.user_id AND uta.task_id = " + taskID);
            rs = stmt.executeQuery();
            while(rs.next()){
                userIdsToDisplay.add(rs.getInt("user_id"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        FilteredList<User> filteredUsers = new FilteredList<>(AppData.getInstance().getUserItems(),s -> true);
        filteredUsers.setPredicate(s -> userIdsToDisplay.contains(s.getUserID()));
        listViewUsers.setItems(filteredUsers);

    }
    @FXML
    public void showProjectTasksAndUsers(){
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        int projId = listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId();

        listViewTasks.setItems(MainAppUIController.getFilteredTasksToDisplay(projId));
        listViewUsers.setItems(MainAppUIController.getUsersToDisplay(projId));

    }

}
