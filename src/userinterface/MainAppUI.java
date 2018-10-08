package userinterface;

import connection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MainAppUI {
    private ObservableList<ObservableList> data;
    private TableView tableView;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> taskItems = FXCollections.observableArrayList();
    @FXML private ListView<String> listViewProjects;
    @FXML private ListView<String> listViewTasks;

    /*
    CREATE TABLE IF NOT EXISTS users(
            USER_ID INT,
            USERNAME varchar(255) NOT NULL,
    USER_EMAIL varchar(255) NOT NULL,
    PRIV_LVL INT,
    PRIMARY KEY(USER_ID)
    */
    public void initialize(){
        try {
            PreparedStatement stmt = DatabaseConnection.con.prepareStatement("INSERT INTO users VALUES('1','Brice','bobjob@gmail.com','3')");
            stmt.executeUpdate();
        } catch (SQLException e){

        }
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
