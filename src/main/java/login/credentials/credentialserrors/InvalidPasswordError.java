package login.credentials.credentialserrors;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidPasswordError extends CredentialsError{
    /**
     * A method that validate password
     * (			# Start of group
     * (?=.*\d)		#   must contains one digit from 0-9
     * (?=.*[a-z])		#   must contains one lowercase characters
     * (?=.*[A-Z])		#   must contains one uppercase characters
     .		#   match anything with previous condition checking
     {6,20}	#   length at least 6 characters and maximum of 20
     * )			# End of group
     *
     * @param password - input password to be validate
     * @return true if the password is in the correct format or else false
     *
     * Correct Format - mkyong1A@
     */
    @Override
    public boolean check(HashMap<String,String> userItems){
        Matcher matcher = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})").matcher(userItems.get("password"));
        if(!matcher.matches()){
            super.errorMessage = "Password must be in the correct format. e.g., mkyong1A@";
            return false;
        }
        return true;
    }
}
