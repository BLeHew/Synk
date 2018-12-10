package mainapp;

import connection.DBSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import tableobjects.*;
import userinterface.SynkApp;

import java.util.*;

public class MainAppUI {
    @FXML private TableView<TableObject> project;
    @FXML private TableView<TableObject> task;
    @FXML private TableView<TableObject> allUsers;
    @FXML private TableView<TableObject> userToProject;
    @FXML private TableView<TableObject> userToTask;

    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private Button btnRemoveProject;
    @FXML private Button btnRemoveTask;
    @FXML private TextField txtFieldSearch;

    private static final String PROJECT = "project";
    private static final String TASK = "task";
    private static final String ALLUSERS = "allUsers";
    private static final String USERSTOPROJECT = "userToProject";
    private static final String USERSTOTASK = "userToTask";

    private User lastChosenUser;

    public void initialize(){
        project.setItems(AppData.getAll(PROJECT));
    }
    @FXML
    public void showProjectTasksAndUsers() {
        TableObject p = selected(project);
        if(p == null){
            return;
        }
        btnRemoveProject.setDisable(false);
        btnRemoveTask.setDisable(true);
        txtAreaProjectDesc.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaProjectDesc.setText(p.getDesc());

        task.setItems(AppData.getProjectTasks(p.getId()));
        userToProject.setItems(AppData.getUsersAttachedToProject(p.getId()));
        userToTask.getItems().clear();
    }
    @FXML
    public void narrowUsers(){
        TableObject t = selected(task);
        if(t == null){
            return;
        }
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaTaskDesc.setText(t.getDesc());
        userToTask.setItems(AppData.getUsersAttachedToTask(t.getId()));
    }

    @FXML
    public void addTask(){
        if(project.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(getId(project));
            AppData.insertIntoDB(t);
            task.getItems().add(t);
        }
    }
    @FXML
    public void addProject(){
        Project project = new Project();
        AppData.insertIntoDB(project);
        this.project.getItems().add(project);
    }
    @FXML
    public void removeProject(){
        AppData.removeFromDB(selected(project));
        removeSelected(project);
        userToProject.getItems().clear();
        task.getItems().clear();
    }
    @FXML
    public void getUsers(){
        allUsers.setItems(AppData.getAll("users"));
    }
    @FXML
    public void search(){
        allUsers.setItems(AppData.searchUsersWithUsername(txtFieldSearch.getText()));
    }
    @FXML
    public void updateName(TableColumn.CellEditEvent<TableObject,String> c){
        c.getRowValue().setName(c.getNewValue());
        AppData.updateDB(c.getRowValue());
    }
    @FXML
    public void updateTaskPercentage(TableColumn.CellEditEvent<Task,String> taskCell){
        taskCell.getRowValue().setPctComplete(taskCell.getNewValue());
        taskCell.getTableView().refresh();
        AppData.updateDB(taskCell.getRowValue());
    }
    @FXML
    public void removeTask(){
        AppData.removeFromDB(selected(task));
        removeSelected(task);
        userToTask.getItems().clear();
    }
    @FXML
    public void assignUserToProject(){
        if (lastChosenUser == null){
            return;
        }
        HashSet<TableObject> uniqueUsers = new HashSet<>(userToProject.getItems());

        if (uniqueUsers.add(lastChosenUser)) {
            userToProject.getItems().add(lastChosenUser);
            DBSource.insertAssignment(lastChosenUser.getId(), getId(project), -1);
        }
    }
    @FXML
    public void assignUserToTask(){
        if (lastChosenUser == null){
            return;
        }
        HashSet<TableObject> uniqueTaskUsers = new HashSet<>(userToTask.getItems());
        if(!uniqueTaskUsers.add(lastChosenUser)){
            return;
        }

        HashSet<TableObject> uniqueProjectUsers = new HashSet<>(userToProject.getItems());
        int projId = getId(project);
        int taskId = getId(task);
        if(DBSource.insertAssignment(lastChosenUser.getId(),projId,taskId)){
            userToTask.getItems().add(lastChosenUser);
            if(uniqueProjectUsers.add(lastChosenUser)){
                userToProject.getItems().add(lastChosenUser);
            }

        }
    }
    @FXML
    public void removeUserFromTask(){
        if(selected(userToTask) == null){
            return;
        }
        int userId = getId(userToTask);
        int taskId = getId(task);

        removeSelected(userToTask);
        DBSource.removeUserTaskAssignment(userId,taskId);

    }
    @FXML
    public void setChosenUser(MouseEvent mouseEvent){
        TableView tableView = (TableView)mouseEvent.getSource();
        lastChosenUser = (User)tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void removeUserFromProject(){

        if(selected(userToProject) == null){
            return;
        }

        int userId = getId(userToProject);
        int projectId = getId(project);



        userToTask.getItems().remove(selected(userToProject));
        removeSelected(userToProject);
        userToTask.refresh();
        DBSource.removeUserProjectAssignment(userId,projectId);
    }
    @FXML
    public void saveChange(KeyEvent k){
        if(k.getCode().getName().equals("Enter")){
            if(txtAreaProjectDesc.isFocused()){
                selected(project).setDesc(txtAreaProjectDesc.getText());
                DBSource.runQuery("update", selected(project));
                txtAreaProjectDesc.setDisable(true);
            }else if (txtAreaTaskDesc.isFocused()){
                selected(task).setDesc(txtAreaTaskDesc.getText());
                DBSource.runQuery("update", selected(task));
                txtAreaTaskDesc.setDisable(true);
            }

        }


    }
    @FXML
    public void logout(){
        SynkApp.getInstance().gotoLogin();
    }
    public int getId(TableView<TableObject> tableView){
        return selected(tableView).getId();
    }
    public TableObject selected(TableView<TableObject> tableView){
        return tableView.getSelectionModel().getSelectedItem();
    }
    public void removeSelected(TableView<TableObject> tableView){
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
    }

}
