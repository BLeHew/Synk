package userinterface;

import java.io.*;

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
        //System.out.println("1234".hashCode());
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

    /**
     * Displays a new window on top of the main app screen
     * @param formTitle the name of the new window screen
     * @param formFXML the fxml file that needs to be loaded from resources
     */
    public void showForm(String formTitle,String formFXML){
        try {
            Stage formWindow = new Stage();
            formWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource(formFXML))));
            formWindow.setTitle(formTitle);
            formWindow.show();
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
