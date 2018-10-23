package userinterface;

import java.io.IOException;

import mainapp.AppData;
import testing.*;
import connection.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;


public class SynkApp extends Application {
    private Stage stage;
    public static String driver = "com.mysql.jdbc.Driver";

    public static void main(String args[]){
        try {
            Class.forName(driver);
        }catch(Exception e){
            System.err.println("Error in loading driver " + e);
        }
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
            if(SynkConnection.hasConnection()){
                AppData.getInstance().populate();
            }
            //DBTesting.fillDBwithTestData();
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
    public void showForm(String formFXML){
        try {
            Stage formwindow = new Stage();
            formwindow.setScene(new Scene(FXMLLoader.load(getClass().getResource(formFXML))));
            formwindow.show();
        } catch ( IOException x ) {
            x.printStackTrace();
        }
    }
    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
}
