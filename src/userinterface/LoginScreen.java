package userinterface;

import connection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreen{

    @FXML private TextField txtFieldUsername;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private TextField txtFieldError;
    @FXML private Button btnLogin;

    @FXML
    private void login(){
        System.out.println("Logging in");
        try {
            Class.forName(DatabaseConnection.driver);
        }catch(Exception e){
            System.out.println("Error in loading driver" + e);
        }


        DatabaseConnection.userName = txtFieldUsername.getText();
        DatabaseConnection.password = passwordFieldPassword.getText();

        if(!DatabaseConnection.establish()){
            txtFieldError.setText(DatabaseConnection.lastError);
        }else {
            FXMLLoaderController controller = new FXMLLoaderController();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(controller.getParent("mainAppUI.fxml")));
            stage.centerOnScreen();
            stage.show();
        }

    }


}
