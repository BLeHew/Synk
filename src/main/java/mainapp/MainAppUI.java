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

    private static final String PROJECT = "project";
    private static final String TASK = "task";
    private static final String ALLUSERS = "allUsers";
    private static final String USERSTOPROJECT = "usersToProject";
    private static final String USERSTOTASK = "usersToTask";

    private User lastChosenUser;
    private TableViewMapper tableViews;

    public void initialize(){
        tableViews = new TableViewMapper(
                new ArrayList<>(Arrays.asList(
                        tableViewProjects,
                        tableViewTasks,
                        tableViewAllUsers,
                        tableViewUsersToProject,
                        tableViewUsersToTask)));

        tableViewProjects.setItems(AppData.getAll(PROJECT));
    }
    @FXML
    public void showProjectTasksAndUsers() {
        Project project = (Project) tableViews.getSelected(PROJECT);
        btnRemoveProject.setDisable(false);
        btnRemoveTask.setDisable(true);
        txtAreaProjectDesc.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaProjectDesc.setText(project.getDesc());

        tableViewTasks.setItems(AppData.getProjectTasks(project.getId()));
        tableViewUsersToProject.setItems(AppData.getUsersAttachedToProject(project.getId()));
        tableViewUsersToTask.getItems().clear();
    }
    @FXML
    public void narrowUsers(){
        TableObject task = tableViews.getSelected(TASK);
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setDisable(false);
        txtAreaTaskDesc.setText(task.getDesc());
        tableViewUsersToTask.setItems(AppData.getUsersAttachedToTask(task.getId()));
    }

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            Task t = new Task(tableViews.getSelectedId(PROJECT));
            AppData.insertIntoDB(t);
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
        AppData.removeFromDB(tableViews.getSelected(PROJECT));
        tableViews.removeSelected(PROJECT);
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
    public void updateTaskPercentage(TableColumn.CellEditEvent<Task,String> taskCell){
        taskCell.getRowValue().setPctComplete(taskCell.getNewValue());
        taskCell.getTableView().refresh();
        AppData.updateDB(taskCell.getRowValue());
    }
    @FXML
    public void removeTask(){
        AppData.removeFromDB(tableViews.getSelected(TASK));
        tableViews.removeSelected(TASK);
        tableViewUsersToTask.getItems().clear();
    }
    @FXML
    public void assignUserToProject(){
        if (lastChosenUser == null){
            return;
        }
        HashSet<TableObject> uniqueUsers = new HashSet<>(tableViewUsersToProject.getItems());

        if (uniqueUsers.add(lastChosenUser))
            tableViewUsersToProject.getItems().add(lastChosenUser);
            DBSource.insertAssignment(lastChosenUser.getId(),tableViews.getSelectedId(PROJECT),-1);
    }
    @FXML
    public void assignUserToTask(){
        if (lastChosenUser == null){
            return;
        }
        HashSet<TableObject> uniqueTaskUsers = new HashSet<>(tableViewUsersToTask.getItems());
        HashSet<TableObject> uniqueProjectUsers = new HashSet<>(tableViewUsersToProject.getItems());
        if(!uniqueTaskUsers.add(lastChosenUser)){
            return;
        }
        int projId = tableViews.getSelectedId(PROJECT);
        int taskId = tableViews.getSelectedId(TASK);
        if(DBSource.insertAssignment(lastChosenUser.getId(),projId,taskId)){
            tableViewUsersToTask.getItems().add(lastChosenUser);
            if(uniqueProjectUsers.add(lastChosenUser)){
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

        tableViews.removeSelected(USERSTOTASK);
        DBSource.removeUserTaskAssignment(userId,taskId);

    }
    @FXML
    public void setChosenUser(MouseEvent mouseEvent){
        TableView tableView = (TableView)mouseEvent.getSource();
        lastChosenUser = (User)tableView.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void removeUserFromProject(){

        if(tableViews.getSelected(USERSTOPROJECT) == null){
            return;
        }

        int userId = tableViews.getSelectedId(USERSTOPROJECT);
        int projectId = tableViews.getSelectedId(PROJECT);

        tableViewUsersToTask.getItems().remove(tableViewUsersToProject.getSelectionModel().getSelectedItem());
        tableViews.removeSelected("usersToProject");
        tableViewUsersToTask.refresh();
        DBSource.removeUserProjectAssignment(userId,projectId);
    }
    @FXML
    public void saveChange(KeyEvent k){
        if(k.getCode().getName().equals("Enter")){
            if(txtAreaProjectDesc.isFocused()){
                tableViews.getSelected(PROJECT).setDesc(txtAreaProjectDesc.getText());
                DBSource.runQuery("update",tableViews.getSelected(PROJECT));
                txtAreaProjectDesc.setDisable(true);
            }else if (txtAreaTaskDesc.isFocused()){
                tableViews.getSelected(TASK).setDesc(txtAreaTaskDesc.getText());
                DBSource.runQuery("update",tableViews.getSelected(TASK));
                txtAreaTaskDesc.setDisable(true);
            }

        }


    }

}
