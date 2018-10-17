package userinterface;

import Testing.DBTesting;
import connection.SynkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SynkApp extends Application {
    private static SynkApp instance;
    private Stage stage;

    public SynkApp() {
        instance = this;
    }

    public static void main(String args[]) {

        launch(args);
    }

    public static SynkApp getInstance() {
        return instance;
    }

    public Stage getStage() {
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

    private void gotoLogin() {
        try {
            stage.setTitle("Synk Login");
            replaceSceneContent("/fxml/loginScreen.fxml");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
}
