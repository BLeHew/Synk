package mainapp;

import javafx.collections.transformation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import tableobjects.*;
import userinterface.*;

import java.lang.reflect.InvocationTargetException;

public class MainAppUI {
    @FXML private ListView<Project> listViewProjects;
    @FXML private ListView<Task> listViewTasks;
    @FXML private ListView<User> listViewUsers;
    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private AnchorPane anchorPaneTasks;
    @FXML private Button btnRemoveTask;
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    @FXML
    public void addTask(){
        if(listViewProjects.getSelectionModel().getSelectedIndex() > -1){
            MainAppUIController.selectedProjectId = listViewProjects.getSelectionModel().getSelectedItem().getProjId();
        }
        AppData.getInstance().addBlankTask();
    }
    public void initialize(){
        listViewProjects.setItems(AppData.getInstance().getProjItems());
        listViewProjects.setCellFactory(lv -> Project.getCell());
        listViewTasks.setCellFactory(lv -> Task.getCell());
    }
    @FXML
    public void updateProjectName(Event event){
        int projId = listViewProjects.getSelectionModel().getSelectedItem().getProjId();
        String projName = listViewProjects.getSelectionModel().getSelectedItem().getProjName();
        updateDatabase("projects",projId,projName);
    }
    @FXML
    public void updateTaskName(Event event){
        int taskId = listViewTasks.getSelectionModel().getSelectedItem().getTaskID();
        String taskName = listViewTasks.getSelectionModel().getSelectedItem().getTaskName();
        updateDatabase("tasks",taskId,taskName);
    }
    @FXML
    public void removeTask(){
        AppData.getInstance().remove(listViewTasks.getSelectionModel().getSelectedItem());
    }
    public void updateDatabase(String type, int id, String newValue){
        AppData.getInstance().updateDatabase(type,id,newValue);
    }
    @FXML
    public void narrowUsers(){
        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setText(listViewTasks.getSelectionModel().getSelectedItem().getTaskDesc());
        int taskID = listViewTasks.getSelectionModel().getSelectedItem().getTaskID();
        listViewUsers.setItems(MainAppUIController.getFilteredUsersToDisplay(taskID));

    }
    @FXML
    public void showProjectTasksAndUsers(){
        btnRemoveTask.setDisable(true);
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        anchorPaneTasks.setDisable(false);
        txtAreaProjectDesc.setText(listViewProjects.getSelectionModel().getSelectedItem().getProjDesc());
        int projId = listViewProjects.getSelectionModel().getSelectedItem().getProjId();
        listViewTasks.setItems(new FilteredList<>(AppData.getInstance().getTaskItems()).filtered(s->s.getProjID() == projId));
        listViewUsers.setItems(MainAppUIController.getUsersToDisplay(projId));


    }

}
