package login.credentials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.DBSource;

public class CredentialsCheck {
    public static String errorMessage = "SUCCESS";

    public static boolean success(String username, String password, String retypePassword, String email){
        boolean isSuccess = true;
        if(!CredentialChecker.getInstance().check(username,password,retypePassword,email).equals("SUCCESS")){
            errorMessage = CredentialChecker.getInstance().errorMessage();
            isSuccess = false;
        }
        return isSuccess;
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
