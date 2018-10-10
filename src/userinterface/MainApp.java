package userinterface;

import User.User;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.SynkConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {
    private Stage stage;
    private User user;

    public static void main(String args[]){
        //System.out.println("bobjob@gmail.com".hashCode());
        launch(args);
    }

    private static MainApp instance;

    public MainApp(){
        instance = this;
    }
    public static MainApp getInstance(){
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
            //Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void gotoLogin() {
        try {
            stage.setTitle("Synk Login");
            replaceSceneContent("loginScreen.fxml");
        } catch (Exception ex) { }
    }

    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
}
