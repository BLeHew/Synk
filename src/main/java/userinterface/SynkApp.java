package userinterface;

import connection.Database;
import javafx.application.Application;
import javafx.stage.Stage;


public class SynkApp extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoMainUI();
            //gotoLogin();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @Override
    public void stop(){
        Database.con.close();
    }

    public static void gotoMainUI(){
        try {
            stage.setTitle("Synk");
            new ScreenManager().replaceSceneContent("/fxml/mainAppUI.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void gotoLogin() {
        try {
            stage.setTitle("Synk Login");
            new ScreenManager().replaceSceneContent("/fxml/loginScreen.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

