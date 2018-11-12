package mainapp;

import connection.DBSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import tableobjects.*;

import java.util.HashSet;

public class MainAppUI {
    @FXML private TableView<TableObject> tableViewProjects;
    @FXML private TableView<TableObject> tableViewTasks;
    @FXML private TableView<TableObject> tableViewAllUsers;
    @FXML private TableView<TableObject> tableViewUsers;

    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private AnchorPane anchorPaneTasks;
    @FXML private Button btnRemoveProject;
    @FXML private Button btnRemoveTask;
    @FXML private TextField txtFieldSearch;

    private TableObject selectedProject;
    private TableObject selectedTask;
    private TableObject selectedUser;


    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id


    public void initialize(){
        tableViewProjects.setItems(DBSource.getItems("project","SELECT * FROM project"));
        tableViewProjects.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    selectedProject = row.getItem();
                    showProjectTasksAndUsers(selectedProject);
                }
            });
            return row ;
        });
        tableViewTasks.setRowFactory( tv -> {
            TableRow<TableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null) {
                    selectedTask = row.getItem();
                    narrowUsers(selectedTask);
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
        anchorPaneTasks.setDisable(false);
        txtAreaProjectDesc.setText(project.getDesc());

        if(null != p.getTasks()){
            tableViewTasks.setItems(FXCollections.observableArrayList(p.getTasks()));
        }
        if (null != p.getUsers()){
            tableViewUsers.setItems(FXCollections.observableArrayList(p.getUsers()));
        }

    }
    public void narrowUsers(TableObject t){
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setText(t.getDesc());
        tableViewUsers.setItems(DBSource.getItems("users","CALL GetUsersAttachedToTask(" + t.getId() + ")"));
    }

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(selectedProject.getId());
            Project p = (Project)selectedProject;
            p.getTasks().add(t);
            t.insertIntoDB();
            tableViewTasks.getItems().add(t);
        }
    }
    @FXML
    public void addProject(){
        Project p = new Project();
        p.insertIntoDB();
        tableViewProjects.getItems().add(p);
    }
    @FXML
    public void removeProject(){
        tableViewProjects
                .getItems()
                .remove(tableViewProjects.getSelectionModel().getSelectedIndex())
                .removeFromDB();

    }
    @FXML
    public void refreshProjects(){
        tableViewProjects.setItems(DBSource.getItems("project","SELECT * FROM project"));
    }
    @FXML
    public void getUsers(){
        tableViewAllUsers.setItems(DBSource.getItems("users","SELECT * FROM users"));
    }
    @FXML
    public void search(){
        tableViewAllUsers.setItems(DBSource
                .getItems("users","SELECT * FROM users WHERE username LIKE '%" + txtFieldSearch.getText() + "%'"));
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
        tableViewTasks
                .getItems()
                .remove(tableViewTasks.getSelectionModel().getSelectedIndex())
                .removeFromDB();
    }
    @FXML
    public void assignUser(){
        HashSet<TableObject> uniqueUsers = new HashSet<>(tableViewUsers.getItems());

        if(!uniqueUsers.add(tableViewAllUsers.getSelectionModel().getSelectedItem())){
            return;
        }

        int userId = tableViewAllUsers.getSelectionModel().getSelectedItem().getId();
        int projId = tableViewProjects.getSelectionModel().getSelectedItem().getId();
        int taskId;
        if(tableViewTasks.getSelectionModel().getSelectedIndex() == -1){
            taskId = -1;
        }else{ taskId = tableViewTasks.getSelectionModel().getSelectedItem().getId(); }

        if(DBSource.insertAssignment(userId,projId,taskId)){
            tableViewUsers.getItems().add(tableViewAllUsers.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    public void removeUserAssign(){

    }
    @FXML
    public void saveChange(KeyEvent k){
        if(k.getCode().getName().equals("Enter")){
            selectedProject.setDesc(txtAreaProjectDesc.getText());
            selectedProject.updateDB();
            txtAreaProjectDesc.setDisable(true);
        }

    }



}
