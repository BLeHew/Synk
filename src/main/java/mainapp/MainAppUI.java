package mainapp;

import connection.DBSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import tableobjects.*;

import java.util.*;

public class MainAppUI {
    @FXML private TableView<TableObject> tableViewProjects;
    @FXML private TableView<TableObject> tableViewTasks;
    @FXML private TableView<TableObject> tableViewAllUsers;
    @FXML private TableView<TableObject> tableViewUsersToProject;
    @FXML private TableView<TableObject> tableViewUsersToTask;

    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private Button btnRemoveProject;
    @FXML private Button btnRemoveTask;
    @FXML private TextField txtFieldSearch;

    private Project selectedProject;
    private Task selectedTask;
    private User lastChosenUser;
    private TableViewMapper tableViews;

    public void initialize(){
        tableViews = new TableViewMapper(
                new ArrayList<>(Arrays.asList(tableViewProjects,
                        tableViewTasks,
                        tableViewAllUsers,
                        tableViewUsersToProject,
                        tableViewUsersToTask)));

        tableViewProjects.setItems(AppData.getAll("project"));
        tableViewProjects.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    selectedProject = (Project)row.getItem();
                    showProjectTasksAndUsers(selectedProject);
                }
            });
            return row ;
        });
        tableViewTasks.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    selectedTask = (Task)row.getItem();
                    narrowUsers(selectedTask);
                }
            });
            return row ;
        });
        tableViewUsersToProject.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    lastChosenUser = (User)row.getItem();
                }
            });
            return row ;
        });
        tableViewAllUsers.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    lastChosenUser = (User)row.getItem();
                }
            });
            return row ;
        });
    }
    public void showProjectTasksAndUsers(TableObject project) {
        Project p = (Project) project;

        btnRemoveProject.setDisable(false);
        btnRemoveTask.setDisable(true);
        txtAreaProjectDesc.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaProjectDesc.setText(project.getDesc());

        tableViewTasks.setItems(FXCollections.observableArrayList(p.getTasks()));
        tableViewUsersToProject.setItems(FXCollections.observableArrayList(p.getUsers()));
        tableViewUsersToTask.setItems(FXCollections.observableArrayList());

    }
    public void narrowUsers(TableObject task){
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaTaskDesc.setText(task.getDesc());
        tableViewUsersToTask.setItems(AppData.getUsersAttachdToTask(task.getId()));
    }

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(selectedProject.getId());
            AppData.insertIntoDB(t);
            selectedProject.getTasks().add(t);
            tableViewTasks.getItems().add(t);
        }
    }
    @FXML
    public void addProject(){
        Project project = new Project();
        AppData.insertIntoDB(project);
        tableViewProjects.getItems().add(project);
    }
    @FXML
    public void removeProject(){
        tableViewProjects.getItems().remove(tableViewProjects.getSelectionModel().getSelectedIndex());
        tableViewUsersToProject.getItems().clear();
        tableViewTasks.getItems().clear();
        AppData.removeFromDB(selectedProject);
    }
    @FXML
    public void getUsers(){
        tableViewAllUsers.setItems(AppData.getAll("users"));
    }
    @FXML
    public void search(){
        tableViewAllUsers.setItems(AppData.searchUsersWithUsername(txtFieldSearch.getText()));
    }
    @FXML
    public void updateName(TableColumn.CellEditEvent<TableObject,String> c){
        c.getRowValue().setName(c.getNewValue());
        c.getRowValue().updateDB();
    }
    @FXML
    public void updateTaskPercentage(TableColumn.CellEditEvent<Task,String> c){
        c.getRowValue().setPctComplete(c.getNewValue());
        c.getTableView().refresh();
        c.getRowValue().updateDB();
    }
    @FXML
    public void removeTask(){
        tableViewTasks.getItems().remove(tableViewTasks.getSelectionModel().getSelectedIndex());
        tableViewUsersToTask.getItems().clear();
        selectedProject.removeTask((Task)tableViewTasks.getSelectionModel().getSelectedItem());
        AppData.removeFromDB(tableViewTasks.getSelectionModel().getSelectedItem());
    }
    @FXML
    public void assignUserToProject(){
        if (lastChosenUser == null){
            return;
        }
        if(selectedProject.getUsers().add(lastChosenUser)){
            tableViewUsersToProject.getItems().add(lastChosenUser);
        }
    }
    @FXML
    public void assignUserToTask(){
        HashSet<TableObject> uniqueTaskUsers = new HashSet<>(tableViewUsersToTask.getItems());
        if (lastChosenUser == null){
            return;
        }
        if(!uniqueTaskUsers.add(lastChosenUser)){
            return;
        }
        int projId = tableViews.getId("projects");
        int taskId;
        taskId = tableViewTasks.getSelectionModel().getSelectedIndex() == -1 ?
                -1 : tableViewTasks.getSelectionModel().getSelectedItem().getId();

        if(DBSource.insertAssignment(lastChosenUser.getId(),projId,taskId)){
            tableViewUsersToTask.getItems().add(lastChosenUser);
            if(selectedProject.getUsers().add(lastChosenUser)){
                tableViewUsersToProject.getItems().add(lastChosenUser);
            }

        }
    }
    @FXML
    public void removeUserFromTask(){
        if(tableViewUsersToTask.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        int userId = tableViewUsersToTask.getSelectionModel().getSelectedItem().getId();
        int taskId = tableViewTasks.getSelectionModel().getSelectedItem().getId();

        tableViewUsersToTask.getItems().remove(tableViewUsersToTask.getSelectionModel().getSelectedIndex());

        DBSource.removeUserTaskAssignment(userId,taskId);

    }
    @FXML
    public void removeUserFromProject(){
        if(tableViewUsersToProject.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        int userId = tableViewUsersToProject.getSelectionModel().getSelectedItem().getId();
        int projectId = tableViewProjects.getSelectionModel().getSelectedItem().getId();

        tableViewUsersToProject.getItems().remove(tableViewUsersToProject.getSelectionModel().getSelectedIndex());

        DBSource.removeUserProjectAssignment(userId,projectId);
    }
    @FXML
    public void saveChange(KeyEvent k){
        if(k.getCode().getName().equals("Enter")){
            if(txtAreaProjectDesc.isFocused()){
                selectedProject.setDesc(txtAreaProjectDesc.getText());
                DBSource.runQuery("update",selectedProject);
                txtAreaProjectDesc.setDisable(true);
            }else if (txtAreaTaskDesc.isFocused()){
                selectedTask.setDesc(txtAreaTaskDesc.getText());
                DBSource.runQuery("update",selectedTask);
                txtAreaTaskDesc.setDisable(true);
            }

        }


    }


}
