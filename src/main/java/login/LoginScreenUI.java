package login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import login.credentials.CredentialsCheck;
import userinterface.SynkApp;

public class LoginScreenUI {

    @FXML private TextField txtFieldUsername;
    @FXML private TextField txtFieldError;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private PasswordField passwordFieldReType;
    @FXML private AnchorPane paneRegister;


    private boolean clicked = false;

    @FXML
    private void login(){
        System.out.println("Logging in");

        if(!CredentialsCheck.hasErrors(txtFieldUsername.getText(),passwordFieldPassword.getText())){
            txtFieldError.setText(CredentialsCheck.errorMessage);
        }else {
            SynkApp.gotoMainUI();
        }
    }
    @FXML
    private void register(){
        txtFieldError.clear();
        //keeping track of if the register button has been clicked yet, and change our logic depending on that.
        if(!clicked) {
            System.out.println("Registering");
            SynkApp.stage.setTitle("Register");
            changeUseability();
            clicked = true;
        }else {
            if(fieldsAreEmpty()){
                txtFieldError.setText("Please fill in all fields.");
            }else if(CredentialsCheck.success(txtFieldUsername.getText(),passwordFieldPassword.getText(),passwordFieldReType.getText(),txtFieldEmail.getText())){
                SynkApp.stage.setTitle("Synk Login");
                changeUseability();
                clicked = false;
            }else {
                txtFieldError.setText(CredentialsCheck.errorMessage);
            }
        }

    }

    /**
     * Checks the fields required for registering, if any of them are null will return false
     * @return true if any of the fields are empty
     */
    private boolean fieldsAreEmpty(){
        return txtFieldUsername.getText().length() < 1 ||
                passwordFieldPassword.getText().length() < 1 ||
                passwordFieldReType.getText().length() < 1||
                txtFieldEmail.getText().length() < 1;
    }
    /**
     * Method to show/hide and disable/enable parts of the ui for registering, allows the same screen to be reused for
     * logging in and registering.
     */
    private void changeUseability(){
        passwordFieldReType.clear();
        txtFieldEmail.clear();
        paneRegister.setVisible(!paneRegister.isVisible());
    }
    @FXML
    private void cancelRegistration(){
        txtFieldError.clear();
        changeUseability();
        SynkApp.stage.setTitle("Synk Login");
        clicked = false;
    }
}
