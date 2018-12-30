package login.credentials.credentialserrors;

import connection.Database;

import java.util.HashMap;

public class NoConnectionError extends CredentialsError {
    @Override
    public boolean check(HashMap<String,String> userItems){
        if (!Database.con.isRunning()){
            super.errorMessage = "No connection to database.";
            return false;
        }
        return true;
    }
}
