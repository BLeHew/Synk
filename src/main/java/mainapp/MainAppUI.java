package mainapp;

import connection.Query;
import connection.DBSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

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
            listViewTasks.getItems().add(AppData.addBlankTask(listViewProjects.getSelectionModel().getSelectedItem().getId()));
        }
    }
    @FXML
    public void addProject(){
        listViewProjects.getItems().add(DBSource.addBlankProject());
    }
    @SuppressWarnings("unchecked")
    public void initialize(){
        listViewProjects.setItems(DBSource.getItems("project",Query.selectAll("projects")));
        listViewProjects.setCellFactory(lv -> Project.getCell());
        listViewTasks.setCellFactory(lv -> Task.getCell());
    }
    @FXML
    public void updateProjectName(ActionEvent event){
        int projId = listViewProjects.getSelectionModel().getSelectedItem().getId();
        String projName = listViewProjects.getSelectionModel().getSelectedItem().getName();
        updateDatabase("projects",projId,projName);
    }
    @FXML
    public void updateTaskName(){
        int taskId = listViewTasks.getSelectionModel().getSelectedItem().getId();
        String taskName = listViewTasks.getSelectionModel().getSelectedItem().getName();
        updateDatabase("tasks",taskId,taskName);
    }
    @FXML
    public void removeTask(){
        listViewTasks.getItems().remove(listViewTasks.getSelectionModel().getSelectedIndex());
        AppData.remove(listViewTasks.getSelectionModel().getSelectedItem());
    }
    public void updateDatabase(String type, int id, String newValue){
        AppData.updateDatabase(type,id,newValue);
    }
    @FXML
    @SuppressWarnings("unchecked")
    public void narrowUsers(){
        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setText(listViewTasks.getSelectionModel().getSelectedItem().getDesc());
        int taskID = listViewTasks.getSelectionModel().getSelectedItem().getId();
        listViewUsers.setItems(DBSource.getItems("user",Query.getUserTaskAttached(taskID)));
    }
    @FXML
    @SuppressWarnings("unchecked")
    public void showProjectTasksAndUsers(){
        btnRemoveTask.setDisable(true);
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        anchorPaneTasks.setDisable(false);
        txtAreaProjectDesc.setText(listViewProjects.getSelectionModel().getSelectedItem().getDesc());
        int projId = listViewProjects.getSelectionModel().getSelectedItem().getId();

        listViewTasks.setItems(DBSource.getItems("task", Query.getProjsTasksAttached(projId)));
        listViewUsers.setItems(DBSource.getItems("user",Query.getUserProjAttached(projId)));

    }

}
