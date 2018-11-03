package mainapp;

import connection.Query;
import connection.DBSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

public class MainAppUI {
    @FXML private TableView<Project> tableViewProjects;
    @FXML private TableView<Task> tableViewTasks;
    @FXML private ListView<User> listViewUsers;
    @FXML private TextArea txtAreaProjectDesc;
    @FXML private TextArea txtAreaTaskDesc;
    @FXML private AnchorPane anchorPaneTasks;
    @FXML private Button btnRemoveTask;
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    @FXML
    public void addTask(){
        if(tableViewProjects.getSelectionModel().getSelectedIndex() > -1){
            tableViewTasks.getItems().add(AppData.addBlankTask(tableViewProjects.getSelectionModel().getSelectedItem().getId()));
        }
    }
    @FXML
    public void addProject(){
        tableViewProjects.getItems().add(DBSource.insertItem(new Project()));
    }
    @SuppressWarnings("unchecked")
    public void initialize(){
        tableViewProjects.setItems(DBSource.getItems("project",Query.selectAll("projects")));
    }
    @FXML
    public void updateProjectName(TableColumn.CellEditEvent<Project,String> c){
        updateDatabase("projects",c.getRowValue().getId(),c.getNewValue());
    }
    @FXML
    public void updateTaskName(TableColumn.CellEditEvent<Task,String> c){
        updateDatabase("tasks",c.getRowValue().getId(),c.getNewValue());
        if (c.getNewValue().length() == 0){
            removeTask();
        }

    }
    @FXML
    public void updateTaskPercentage(TableColumn.CellEditEvent<Task,String> c){
        c.getRowValue().setPctComplete(c.getNewValue());
        tableViewTasks.refresh();
    }
    @FXML
    public void removeTask(){
        tableViewTasks.getItems().remove(tableViewTasks.getSelectionModel().getSelectedIndex());
        AppData.remove(tableViewTasks.getSelectionModel().getSelectedItem());
    }
    public void updateDatabase(String type, int id, String newValue){
        AppData.updateDatabase(type,id,newValue);
    }
    @FXML
    @SuppressWarnings("unchecked")
    public void narrowUsers(){
        if(tableViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        btnRemoveTask.setDisable(false);
        txtAreaTaskDesc.setText(tableViewTasks.getSelectionModel().getSelectedItem().getDesc());
        int taskID = tableViewTasks.getSelectionModel().getSelectedItem().getId();
        listViewUsers.setItems(DBSource.getItems("user",Query.getUserTaskAttached(taskID)));
    }
    @FXML
    @SuppressWarnings("unchecked")
    public void showProjectTasksAndUsers(){
        btnRemoveTask.setDisable(true);
        if(tableViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        anchorPaneTasks.setDisable(false);
        txtAreaProjectDesc.setText(tableViewProjects.getSelectionModel().getSelectedItem().getDesc());
        int projId = tableViewProjects.getSelectionModel().getSelectedItem().getId();

        tableViewTasks.setItems(DBSource.getItems("task", Query.getProjsTasksAttached(projId)));
        listViewUsers.setItems(DBSource.getItems("user",Query.getUserProjAttached(projId)));

    }

}
