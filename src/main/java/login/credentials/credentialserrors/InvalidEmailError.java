package login.credentials.credentialserrors;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidEmailError extends CredentialsError {

    /**
     * A method that validate an email address.
     * @param userItems the items to be checked, should contain an email key,value pair
     * @return - true if the email is in the correct format else false
     *
     * Correct Format - john.doe@hotmail.com
     */
    @Override
    public boolean check(HashMap<String,String> userItems){
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(userItems.get("email"));
        if (!matcher.find()){
            super.errorMessage = "Must enter valid email. e.g., john.doe@hotmail.com";
            return false;
        }
        return true;
    }
}
