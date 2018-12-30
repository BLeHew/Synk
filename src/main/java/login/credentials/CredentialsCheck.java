package login.credentials;

import connection.Database;

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
        if (!Database.con.isRunning()) {
            errorMessage = "No Connection to Database";
            return true;
        } else if (!Database.validateCredentials(username, password)) {
            return true;
        }
        return false;
    }

}
