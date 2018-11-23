package login.credentials.credentialserrors;

import connection.DBSource;

import java.util.HashMap;

public class NoConnectionError extends CredentialsError {
    @Override
    public boolean check(HashMap<String,String> userItems){
        if (!DBSource.con.isRunning()){
            super.errorMessage = "No connection to database.";
            return false;
        }
        return true;
    }
}
