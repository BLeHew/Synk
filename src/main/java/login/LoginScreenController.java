package login;

import connection.SynkConnection;
import userinterface.SynkApp;

public class LoginScreenController {

    private static final int NOCONNECTION = 0;
    private static final int USERTOSHORT = 1;
    private static final int PASSTOOSHORT = 2;
    private static final int PASSDOESNTMATCH = 3;
    private static final int INVALIDEMAIL = 4;
    private static final int SUCCESS = 5;
    private static final int USERALREADYEXISTS = 6;

    public String errorMessage;

    public void switchToMainAppScreen(){
        SynkApp.getInstance().getStage().setTitle("Synk");
        SynkApp.getInstance().gotoMainUI();
    }
    public boolean success(String username, String password, String retypePassword, String email){
        return getErrorType(username,password,retypePassword,email) == SUCCESS;
    }
    public int getErrorType(String username, String password, String retypePassword, String email){
        if (!SynkConnection.hasConnection()){
            errorMessage = "No connection to database.";
            return NOCONNECTION;
        }else if(username.length() < 4) {
            errorMessage = "Username must be longer than 3 characters";
            return USERTOSHORT;
        }else if(password.length() < 8){
            errorMessage = "Password must be longer than 7 characters";
            return PASSTOOSHORT;
        }else if(!password.equals(retypePassword)){
            errorMessage = "Passwords must match";
            return PASSDOESNTMATCH;
        }else if(email.length() < 3){
            errorMessage = "Must enter valid email.";
            return INVALIDEMAIL;
        }else if(!SynkConnection.registerCredentials(username,password,email)) {
            errorMessage = "Username already exists";
            return USERALREADYEXISTS;
        }else {
            return SUCCESS;
        }
    }
    public boolean hasErrors(String username,String password) {
        if (!SynkConnection.hasConnection) {
            errorMessage = "No Connection to Database";
            return true;
        } else if (!SynkConnection.validateCredentials(username, password)) {
            errorMessage = SynkConnection.lastError;
            return true;
        }
        return false;
    }
}
