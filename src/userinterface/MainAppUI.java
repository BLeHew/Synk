package userinterface;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import projects.Project;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class MainAppUI {
    private ObservableList<ObservableList> data;
    private TableView tableView;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> taskItems = FXCollections.observableArrayList();
    @FXML private ListView<String> listViewProjects;
    @FXML private ListView<String> listViewTasks;

    public void initialize(){
        items.add("Project 1");
        items.add("Project 2");
        items.add("Project 3");
        items.add("Project 4");
        listViewProjects.setItems(items);
    }
    @FXML
    public void showProjectTasks(){
        taskItems.clear();
        listViewTasks.refresh();
        switch(listViewProjects.getSelectionModel().getSelectedIndex()){
            case 0:
                taskItems.add("Proj1Task1");
                taskItems.add("Proj1Task2");
                taskItems.add("Proj1Task3");
                taskItems.add("Proj1Task4");
                break;
            case 1:
                taskItems.add("Proj2Task1");
                taskItems.add("Proj2Task2");
                taskItems.add("Proj2Task3");
                taskItems.add("Proj2Task4");
                break;
            case 2:
                taskItems.add("Proj3Task1");
                taskItems.add("Proj3Task2");
                taskItems.add("Proj3Task3");
                taskItems.add("Proj3Task4");
                break;
            case 3:
                taskItems.add("Proj4Task1");
                taskItems.add("Proj4Task2");
                taskItems.add("Proj4Task3");
                taskItems.add("Proj4Task4");
                break;
            case 4:
                taskItems.add("Proj5Task1");
                taskItems.add("Proj5Task2");
                taskItems.add("Proj5Task3");
                taskItems.add("Proj5Task4");
                break;
                default:
                    break;
        }
        listViewTasks.setItems(taskItems);
    }

}
