package mainapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;
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
    public void displayForm(ActionEvent event) {
        //Use elements embedded in the fxml file for the button in order to display the correct form.
        //also uses the name on the button in order to set the title of the form
        SynkApp.getInstance().showForm(((Button) event.getSource()).getText(),((Button) event.getSource()).getId());
    }
    public void initialize(){
        listViewProjects.setItems(AppData.getProjItems());

        /*
        listViewProjects.setCellFactory(lv -> {
            TextFieldListCell<Project> cell = new TextFieldListCell<>();
            cell.setConverter(new StringConverter<Project>() {
                @Override
                public String toString(Project project) {
                    return project.getProjName();
                }
                @Override
                public Project fromString(String string) {
                    Project proj = cell.getItem();
                    proj.setProjDesc(string);
                    AppData.getInstance().getProjItems().get(cell.getIndex()).setProjName(string);
                    return proj ;
                }
            });
            return cell;
        });
        */
    }
    @FXML
    public void changeProjectName(){

    }
    @FXML
    public void narrowUsers(){
        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        int taskID = listViewTasks.getItems().get(listViewTasks.getSelectionModel().getSelectedIndex()).getTaskID();
        listViewUsers.setItems(MainAppUIController.getFilteredUsersToDisplay(taskID));

    }
    @FXML
    public void showProjectTasksAndUsers(){
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        int projId = listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId();

        listViewTasks.setItems(MainAppUIController.getFilteredTasksToDisplay(projId));
        listViewUsers.getItems().filtered(s -> MainAppUIController.getUsersToDisplay(projId).contains(projId));

    }

}
