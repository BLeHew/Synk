package login.credentials.credentialserrors;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidPasswordError extends CredentialsError{
    @Override
    public boolean check(HashMap<String,String> userItems){
        //Matcher matcher = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})").matcher(userItems.get("password"));
        if(userItems.get("password").length() < 4){
            //super.errorMessage = "Password must be in the correct format. e.g., mkyong1A@";
            super.errorMessage = "Password must be at least 4 characters long.";
            return false;
        }
        return true;
    }
}
