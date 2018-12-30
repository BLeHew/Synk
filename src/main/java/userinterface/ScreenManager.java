package userinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import static userinterface.SynkApp.stage;

public class ScreenManager {
    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
