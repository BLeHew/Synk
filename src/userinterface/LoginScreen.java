package userinterface;

import connection.SynkConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen{

    @FXML private TextField txtFieldUsername;
    @FXML private TextField txtFieldError;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private PasswordField passwordFieldRetype;
    @FXML private Button btnCancelRegistration;
    @FXML private Button btnRegister;
    @FXML private Button btnLogin;
    private boolean clicked = false;
    private static final double SHIFTDOWN = 75;
    private static final double SHIFTUP = -75;
    @FXML
    private void login(){
        System.out.println("Logging in");

        if(!SynkConnection.validateCredentials(txtFieldUsername.getText(),passwordFieldPassword.getText())){
            txtFieldError.setText(SynkConnection.lastError);
        }else {
            MainApp.getInstance().getStage().setTitle("Synk");
            try {
                MainApp.getInstance().replaceSceneContent("mainAppUI.fxml");
            }catch (Exception e){

            }
        }

    }
    @FXML
    private void register(){
        if(!clicked) {
            System.out.println("Registering");
            MainApp.getInstance().getStage().setTitle("Register");
            changeUseability();
            shiftScreenItems(SHIFTDOWN);
            clicked = true;
        }else {
            if(!passwordFieldPassword.getText().equals(passwordFieldRetype.getText())){
                txtFieldError.setText("Passwords must match.");
            }else {
                txtFieldError.clear();
                MainApp.getInstance().getStage().setTitle("Login");
                changeUseability();
                shiftScreenItems(SHIFTUP);
                clicked = false;
            }
        }

    }

    /**
     * Method to show/hide and disable/enable parts of the ui for registering, allows the same screen to be reused for
     * logging in and registering.
     */
    private void changeUseability(){
        passwordFieldRetype.clear();
        txtFieldEmail.clear();
        passwordFieldRetype.setDisable(!passwordFieldRetype.isDisabled());
        passwordFieldRetype.setVisible(!passwordFieldRetype.isVisible());
        txtFieldEmail.setDisable(!txtFieldEmail.isDisabled());
        txtFieldEmail.setVisible(!txtFieldEmail.isVisible());
        btnCancelRegistration.setDisable(!btnCancelRegistration.isDisabled());
        btnCancelRegistration.setVisible(!btnCancelRegistration.isVisible());
    }
    @FXML
    private void cancelRegistration(){
        MainApp.getInstance().getStage().setTitle("Login");
        changeUseability();
        shiftScreenItems(SHIFTUP);
        clicked = false;
    }
    private void shiftScreenItems(double movement){
        MainApp.getInstance().getStage().setHeight(MainApp.getInstance().getStage().getHeight() + movement);
        btnRegister.setLayoutY(btnRegister.getLayoutY() + movement);
        btnLogin.setLayoutY(btnLogin.getLayoutY() + movement);
        txtFieldError.setLayoutY(txtFieldError.getLayoutY() + movement);
    }
}
