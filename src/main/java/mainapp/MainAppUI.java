package mainapp;

import connection.DBSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    }
    @FXML
    public void showProjectTasksAndUsers() {
        Project p = (Project) tableViews.getSelected("project");

        btnRemoveProject.setDisable(false);
        btnRemoveTask.setDisable(true);
        txtAreaProjectDesc.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaProjectDesc.setText(p.getDesc());

        tableViewTasks.setItems(FXCollections.observableArrayList(p.getTasks()));
        tableViewUsersToProject.setItems(FXCollections.observableArrayList(p.getUsers()));
        tableViewUsersToTask.setItems(FXCollections.observableArrayList());

    }
    @FXML
    public void narrowUsers(){
        TableObject task = tableViews.getSelected("task");
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaTaskDesc.setText(task.getDesc());
        tableViewUsersToTask.setItems(AppData.getUsersAttachedToTask(task.getId()));
    }

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(tableViews.getSelectedId("project"));
            AppData.insertIntoDB(t);
            ((Project)tableViews.getSelected("project")).getTasks().add(t);
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
        AppData.removeFromDB(tableViews.getSelected("project"));
        tableViewProjects.getItems().remove(tableViewProjects.getSelectionModel().getSelectedIndex());
        tableViewUsersToProject.getItems().clear();
        tableViewTasks.getItems().clear();
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
        AppData.updateDB(c.getRowValue());
    }
    @FXML
    public void updateTaskPercentage(TableColumn.CellEditEvent<Task,String> c){
        c.getRowValue().setPctComplete(c.getNewValue());
        c.getTableView().refresh();
        AppData.updateDB(c.getRowValue());
    }
    @FXML
    public void removeTask(){
        AppData.removeFromDB(tableViewTasks.getSelectionModel().getSelectedItem());
        tableViewTasks.getItems().remove(tableViewTasks.getSelectionModel().getSelectedIndex());
        tableViewUsersToTask.getItems().clear();
        ((Project)tableViews.getSelected("project")).removeTask((Task)tableViewTasks.getSelectionModel().getSelectedItem());
    }
    @FXML
    public void assignUserToProject(){
        if (lastChosenUser == null){
            return;
        }
        if(((Project)tableViews.getSelected("project")).getUsers().add(lastChosenUser)){
            tableViewUsersToProject.getItems().add(lastChosenUser);
        }
    }
    @FXML
    public void assignUserToTask(){
        if (lastChosenUser == null){
            return;
        }
        HashSet<TableObject> uniqueTaskUsers = new HashSet<>(tableViewUsersToTask.getItems());
        if(!uniqueTaskUsers.add(lastChosenUser)){
            return;
        }
        int projId = tableViews.getSelectedId("project");
        int taskId;
        taskId = tableViewTasks.getSelectionModel().getSelectedIndex() == -1 ?
                -1 : tableViewTasks.getSelectionModel().getSelectedItem().getId();

        if(DBSource.insertAssignment(lastChosenUser.getId(),projId,taskId)){
            tableViewUsersToTask.getItems().add(lastChosenUser);
            if(((Project)tableViews.getSelected("project")).getUsers().add(lastChosenUser)){
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
    public void setChosenUser(MouseEvent mouseEvent){
        TableView tableView = (TableView)mouseEvent.getSource();
        lastChosenUser = (User)tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void removeUserFromProject(){

        if(tableViews.getSelected("usersToProject") == null){
            return;
        }

        int userId = tableViews.getSelectedId("usersToProject");
        int projectId = tableViewProjects.getSelectionModel().getSelectedItem().getId();

        ((Project)tableViews.getSelected("project")).removeUser((User)tableViews.getSelected("usersToProject"));

        tableViewUsersToTask.getItems().remove(tableViewUsersToProject.getSelectionModel().getSelectedItem());
        tableViews.removeSelected("usersToProject");
        tableViewUsersToTask.refresh();
        DBSource.removeUserProjectAssignment(userId,projectId);
    }
    @FXML
    public void saveChange(KeyEvent k){
        if(k.getCode().getName().equals("Enter")){
            if(txtAreaProjectDesc.isFocused()){
                tableViews.getSelected("project").setDesc(txtAreaProjectDesc.getText());
                DBSource.runQuery("update",tableViews.getSelected("project"));
                txtAreaProjectDesc.setDisable(true);
            }else if (txtAreaTaskDesc.isFocused()){
                tableViews.getSelected("task").setDesc(txtAreaTaskDesc.getText());
                DBSource.runQuery("update",tableViews.getSelected("task"));
                txtAreaTaskDesc.setDisable(true);
            }

        }


    }


}
