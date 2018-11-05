package mainapp;

import connection.DBSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import tableobjects.Project;
import tableobjects.TableObject;
import tableobjects.Task;
import tableobjects.User;

import java.util.HashSet;

public class MainAppUI {
    @FXML private TableView<TableObject> tableViewProjects;
    @FXML private TableView<TableObject> tableViewTasks;
    @FXML private TableView<TableObject> tableViewAllUsers;
    @FXML private ListView<TableObject> listViewUsers;
    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private AnchorPane anchorPaneTasks;
    @FXML private Button btnRemoveTask;
    @FXML private TextField txtFieldSearch;
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(tableViewProjects.getSelectionModel().getSelectedItem().getId());
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
        tableViewProjects.getSelectionModel().getSelectedItem().removeFromDB();
        tableViewProjects.getItems().remove(tableViewProjects.getSelectionModel().getFocusedIndex());
    }
    public void initialize(){
        tableViewProjects.setItems(DBSource.getItems("project","SELECT * FROM project"));
    }
    @FXML
    public void getUsers(){
        tableViewAllUsers.setItems(DBSource.getItems("users","SELECT * FROM users"));
    }
    @FXML
    public void search(){
        System.out.println("SELECT * FROM users WHERE username LIKE '%" + txtFieldSearch.getText() + "%'");
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
        c.getRowValue().updateDB();
    }
    @FXML
    public void removeTask(){
        tableViewTasks.getSelectionModel().getSelectedItem().removeFromDB();
        tableViewTasks.getItems().remove(tableViewTasks.getSelectionModel().getSelectedIndex());
    }
    @FXML
    public void assignUser(){
        HashSet<TableObject> uniqueUsers = new HashSet<>(listViewUsers.getItems());

        if(!uniqueUsers.add(tableViewAllUsers.getSelectionModel().getSelectedItem())){
            return;
        }

        int userId = tableViewAllUsers.getSelectionModel().getSelectedItem().getId();
        int projId = tableViewProjects.getSelectionModel().getSelectedItem().getId();
        int taskId;
        if(tableViewTasks.getSelectionModel().getSelectedIndex() == -1){
            taskId = -1;
        }else{ taskId = tableViewTasks.getSelectionModel().getSelectedItem().getId(); }
        listViewUsers.getItems().add(tableViewAllUsers.getSelectionModel().getSelectedItem());
        DBSource.insertAssignment(userId,projId,taskId);
    }
    @FXML
    public void narrowUsers(){
        if(tableViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setText(tableViewTasks.getSelectionModel().getSelectedItem().getDesc());
        int taskID = tableViewTasks.getSelectionModel().getSelectedItem().getId();
        listViewUsers.setItems(DBSource.getItems("users","CALL GetUsersAttachedToTask(" + taskID + ")"));
    }
    @FXML
    public void showProjectTasksAndUsers(){
        btnRemoveTask.setDisable(true);
        txtAreaProjectDesc.setDisable(false);
        if(tableViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        anchorPaneTasks.setDisable(false);
        txtAreaProjectDesc.setText(tableViewProjects.getSelectionModel().getSelectedItem().getDesc());
        int projId = tableViewProjects.getSelectionModel().getSelectedItem().getId();
        tableViewTasks.setItems(DBSource.getItems("task", "SELECT * FROM task WHERE proj_id = " + projId));
        listViewUsers.setItems(DBSource.getItems("users","CALL GetUsersAttachedToProject(" + projId + ")"));

    }

}
