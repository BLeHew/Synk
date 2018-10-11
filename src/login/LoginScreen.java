package login;

import connection.SynkConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import userinterface.MainApp;

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
    @FXML private AnchorPane paneRegister;

    private boolean clicked = false;
    private static final double SHIFTDOWN = 50;
    private static final double SHIFTUP = -50;

    private static final int NOCONNECTION = 0;
    private static final int USERTOSHORT = 1;
    private static final int PASSTOSHORT = 2;
    private static final int PASSDOESNTMATCH = 3;
    private static final int NOEMAIL = 4;
    private static final int SUCCESS = 5;


    @FXML
    private void login(){
        System.out.println("Logging in");

        //Make sure we have connection to database
        if(!SynkConnection.hasConnection){
            txtFieldError.setText("No connection to database");
        }else if(!SynkConnection.validateCredentials(txtFieldUsername.getText(),passwordFieldPassword.getText())){
            txtFieldError.setText(SynkConnection.lastError);
        }else {
            MainApp.getInstance().getStage().setTitle("Synk");
            try {
                MainApp.getInstance().getStage().setHeight(450);
                MainApp.getInstance().replaceSceneContent("mainAppUI.fxml");
            }catch (Exception e){

            }
        }

    }
    @FXML
    private void register(){
        txtFieldError.clear();
        //keeping track of if the register button has been clicked yet, and change our logic depending on that.
        if(!clicked) {
            System.out.println("Registering");
            MainApp.getInstance().getStage().setTitle("Register");
            changeUseability();
            shiftScreenItems(SHIFTDOWN);
            clicked = true;
        }else {
            switch(registerErrorType()) {
                case NOCONNECTION:
                    txtFieldError.setText("No connection to database");
                    break;
                case USERTOSHORT: txtFieldError.setText("Username must be longer than 4 characters");
                    break;
                case PASSTOSHORT: txtFieldError.setText("Password must be at least 8 characters");
                    break;
                case PASSDOESNTMATCH: txtFieldError.setText("Passwords must match.");
                    break;
                case NOEMAIL: txtFieldError.setText("Must enter an email.");
                    break;
                case SUCCESS: MainApp.getInstance().getStage().setTitle("Login");
                    changeUseability();
                    shiftScreenItems(SHIFTUP);
                    clicked = false;
                    break;
            }
        }

    }
    private int registerErrorType(){
        if (!SynkConnection.hasConnection){
            return NOCONNECTION;
        }else if(txtFieldUsername.getText().length() < 4) {
            return USERTOSHORT;
        }else if(passwordFieldPassword.getText().length() < 8){
            return PASSTOSHORT;
        }else if(!passwordFieldPassword.getText().equals(passwordFieldRetype.getText())){
            return PASSDOESNTMATCH;
        }else if(txtFieldEmail.getText().length() < 1){
            return NOEMAIL;
        } else {
            return SUCCESS;
        }
    }
    /**
     * Method to show/hide and disable/enable parts of the ui for registering, allows the same screen to be reused for
     * logging in and registering.
     */
    private void changeUseability(){
        passwordFieldRetype.clear();
        txtFieldEmail.clear();
        paneRegister.setVisible(!paneRegister.isVisible());
    }
    @FXML
    private void cancelRegistration(){
        txtFieldError.clear();
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
