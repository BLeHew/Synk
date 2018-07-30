package userinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLLoaderController {
    private Parent parent;

    public Parent getParent(String fxml){
        try{
            parent = new FXMLLoader().load(getClass().getResource(fxml));
        }catch (IOException io){
            System.err.println(io);
        }
        return parent;
    }
}
