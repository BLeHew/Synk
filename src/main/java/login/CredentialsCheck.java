package login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.DBSource;

public class CredentialsCheck {

    private static final int NOCONNECTION = 0;
    private static final int USERTOSHORT = 1;
    private static final int PASSTOOSHORT = 2;
    private static final int PASSDOESNTMATCH = 3;
    private static final int INVALIDEMAIL = 4;
    private static final int SUCCESS = 5;
    private static final int USERALREADYEXISTS = 6;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX =
			Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    public static String errorMessage;

    public static boolean success(String username, String password, String retypePassword, String email){
        return getErrorType(username,password,retypePassword,email) == SUCCESS;
    }
    public static int getErrorType(String username, String password, String retypePassword, String email){
        if (!DBSource.con.isRunning()){
            errorMessage = "No connection to database.";
            return NOCONNECTION;
        }else if(username.length() < 4) {
            errorMessage = "Username must be longer than 3 characters";
            return USERTOSHORT;
        }else if(validatePassword(password) == false){
            errorMessage = "Password must be in the correct format. e.g., mkyong1A@";
            return PASSTOOSHORT;
        }else if(!password.equals(retypePassword)){
            errorMessage = "Passwords must match";
            return PASSDOESNTMATCH;
        }else if(validateEmail(email) == false){
            errorMessage = "Must enter valid email. e.g., john.doe@hotmail.com";
            return INVALIDEMAIL;
        }else if(!DBSource.registerCredentials(username,password,email)) {
            errorMessage = "Username already exists";
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
    
    /**
	* A method that validate an email address.
	* @param emailStr - input email to be validate
	* @return - true if the email is in the correct format else false
	* 
	* Correct Format - john.doe@hotmail.com
	*/
	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}
	
	/**
	 * A method that validate password
	* (			# Start of group
  	* (?=.*\d)		#   must contains one digit from 0-9
  	* (?=.*[a-z])		#   must contains one lowercase characters
    * (?=.*[A-Z])		#   must contains one uppercase characters
  	* (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              	.		#   match anything with previous condition checking
                {6,20}	#   length at least 6 characters and maximum of 20	
	* )			# End of group
	* 
	* @param password - input password to be validate
	* @return true if the password is in the correct format or else false
	* 
	* Correct Format - mkyong1A@
	*/
	public static boolean validatePassword(final String password){
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.matches();
	    	    
	}
}
