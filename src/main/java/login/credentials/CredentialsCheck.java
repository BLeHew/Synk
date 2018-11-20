package login.credentials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.DBSource;

public class CredentialsCheck {

    private static final String NOCONNECTION = "No connection to database.";
    private static final String SUCCESS = "SUCCESS";
    private static final String USERALREADYEXISTS = "Username already exists";

    public static String errorMessage;

    public static boolean success(String username, String password, String retypePassword, String email){
        errorMessage = getErrorType(username,password,retypePassword,email);
        return errorMessage.equals(SUCCESS);
    }
    public static String getErrorType(String username, String password, String retypePassword, String email){
        if (!DBSource.con.isRunning()){
            return NOCONNECTION;
        }
        if(!CredentialChecker.getInstance().check(username,password,retypePassword,email).equals(SUCCESS)){
            errorMessage = CredentialChecker.getInstance().errorMessage();
            return errorMessage;
        }
        else if(!DBSource.registerCredentials(username,password,email)) {
            return USERALREADYEXISTS;
        }else {
            return SUCCESS;
        }
    }
    
    /**
    * A method that checks for error when user type in their credentials.
    * @param username - username input
    * @param password - password input
    * @return true if credential is correct else return "No Connection to the Database"
    */
    public static boolean hasErrors(String username,String password) {
        if (!DBSource.con.isRunning()) {
            errorMessage = "No Connection to Database";
            return true;
        } else if (!DBSource.validateCredentials(username, password)) {
            errorMessage = DBSource.lastError;
            return true;
        }
        return false;
    }

}
