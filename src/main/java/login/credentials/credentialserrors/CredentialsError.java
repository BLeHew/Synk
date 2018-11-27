package login.credentials.credentialserrors;

import java.util.HashMap;

public class CredentialsError {
    protected String errorMessage = "SUCCESS";

    public String getErrorMessage(){
        return errorMessage;
    }

    public boolean check(HashMap<String,String> userItems){
        return true;
    }
}
