package login.credentials.credentialserrors;

import login.credentials.CredentialChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidEmailError extends CredentialsError {

    /**
     * A method that validate an email address.
     * @param emailStr - input email to be validate
     * @return - true if the email is in the correct format else false
     *
     * Correct Format - john.doe@hotmail.com
     */
    @Override
    public boolean check(String username,String password, String passwordReenter, String email){
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        if (!matcher.find()){
            super.errorMessage = "Must enter valid email. e.g., john.doe@hotmail.com";
            return false;
        }
        return true;
    }
}
