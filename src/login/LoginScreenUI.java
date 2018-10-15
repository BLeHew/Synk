package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import userinterface.SynkApp;

public class LoginScreenUI {

    @FXML private TextField txtFieldUsername;
    @FXML private TextField txtFieldError;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private PasswordField passwordFieldRetype;
    @FXML private Button btnCancelRegistration;
    @FXML private Button btnRegister;
    @FXML private Button btnLogin;
    @FXML private AnchorPane paneRegister;

    private LoginScreenController controller = new LoginScreenController();

    private boolean clicked = false;
    private static final double SHIFTDOWN = 50;
    private static final double SHIFTUP = -50;

    @FXML
    private void login(){
        System.out.println("Logging in");

        if(controller.hasErrors(txtFieldUsername.getText(),passwordFieldPassword.getText())){
            txtFieldError.setText(controller.errorMessage);
        }else {
            controller.switchToMainAppScreen();
        }
    }
    @FXML
    private void register(){
        txtFieldError.clear();
        //keeping track of if the register button has been clicked yet, and change our logic depending on that.
        if(!clicked) {
            System.out.println("Registering");
            SynkApp.getInstance().getStage().setTitle("Register");
            changeUseability();
            shiftScreenItems(SHIFTDOWN);
            clicked = true;
        }else {
            if(fieldsAreEmpty()){
                txtFieldError.setText("Please fill in all fields.");
            }else if(controller.success(txtFieldUsername.getText(),passwordFieldPassword.getText(),passwordFieldRetype.getText(),txtFieldEmail.getText())){
                SynkApp.getInstance().getStage().setTitle("Synk Login");
                changeUseability();
                shiftScreenItems(SHIFTUP);
                clicked = false;
            }else {
                txtFieldError.setText(controller.errorMessage);
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
                passwordFieldRetype.getText().length() < 1||
                txtFieldEmail.getText().length() < 1;
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
        SynkApp.getInstance().getStage().setTitle("Synk Login");
        changeUseability();
        shiftScreenItems(SHIFTUP);
        clicked = false;
    }
    private void shiftScreenItems(double movement){
        SynkApp.getInstance().getStage().setHeight(SynkApp.getInstance().getStage().getHeight() + movement);
        btnRegister.setLayoutY(btnRegister.getLayoutY() + movement);
        btnLogin.setLayoutY(btnLogin.getLayoutY() + movement);
        txtFieldError.setLayoutY(txtFieldError.getLayoutY() + movement);
    }
}
