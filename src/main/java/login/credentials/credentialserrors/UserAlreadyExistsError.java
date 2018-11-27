package login.credentials.credentialserrors;

import connection.DBSource;

import java.util.HashMap;

public class UserAlreadyExistsError extends CredentialsError {
    @Override
    public boolean check(HashMap<String,String> userItems){
        if(!DBSource.registerCredentials(userItems.get("username"),
                userItems.get("password"),
                userItems.get("email"))) {
            super.errorMessage = "Username already exists";
            return false;
        }
        return true;
    }
}
