package userinterface;

import connection.SynkConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import projects.Project;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainAppUI {
    private ObservableList<ObservableList> data;
    private TableView tableView;
    ObservableList<Project> projItems = FXCollections.observableArrayList();
    ObservableList<String> taskItems = FXCollections.observableArrayList();
    @FXML private ListView<Project> listViewProjects;
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
        ResultSet rs;
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM projects");
            rs = stmt.executeQuery();
            while(rs.next()){
                projItems.add(new Project(rs.getInt("PROJ_ID"),
                        rs.getString("PROJ_NAME"),
                        rs.getString("PROJ_DESC")));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        listViewProjects.setItems(projItems);
    }
    @FXML
    public void showProjectTasks(){

        taskItems.clear();
        listViewTasks.refresh();
        ResultSet rs;
        int projId = listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId();
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement("SELECT * FROM tasks WHERE Proj_ID =" + projId);
                    //listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId());
            rs = stmt.executeQuery();
            while(rs.next()){
                taskItems.add(rs.getString("TASK_DESC"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        listViewTasks.setItems(taskItems);

    }

}
