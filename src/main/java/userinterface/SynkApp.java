package userinterface;

import Testing.DBTesting;
import connection.SynkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SynkApp extends Application {
    private Stage stage;

    public static void main(String args[]){
        System.out.println("This is a test");
        launch(args);
    }

    private static SynkApp instance;

    public SynkApp(){
        instance = this;
    }
    public static SynkApp getInstance(){
        return instance;
    }
    public Stage getStage(){
        return stage;
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            SynkConnection.establish();
            DBTesting.fillDBwithTestData();
            gotoLogin();
        } catch (Exception ex) {

        }
    }
    public void gotoMainUI(){
        try {
            stage.setTitle("Synk");
            replaceSceneContent("/fxml/mainAppUI.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void gotoLogin() {
        try {
            stage.setTitle("Synk Login");
            replaceSceneContent("/fxml/loginScreen.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
}
