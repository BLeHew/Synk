package login.credentials.credentialserrors;

import java.util.HashMap;

public class PassDoesntMatchError extends CredentialsError{
    @Override
    public boolean check(HashMap<String,String> userItems){
        if(!userItems.get("password").equals(userItems.get("passwordReenter"))){
            super.errorMessage = "Passwords Must Match";
            return false;
        }
        return true;
    }
}
