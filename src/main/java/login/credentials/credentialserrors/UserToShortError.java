package login.credentials.credentialserrors;

import java.util.HashMap;

public class UserToShortError extends CredentialsError{
    @Override
    public boolean check(HashMap<String,String> userItems){
        if(userItems.get("username").length() < 3){
            super.errorMessage = "Username must be longer than 3 characters";
            return false;
        }
        return true;
    }
}
