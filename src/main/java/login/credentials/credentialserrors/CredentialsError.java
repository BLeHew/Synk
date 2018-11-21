package login.credentials.credentialserrors;

public class CredentialsError {
    protected String errorMessage = "SUCCESS";

    public String getErrorMessage(){
        return errorMessage;
    }

    public boolean check(String username,String password, String passwordReenter, String email){
        return true;
    }
}
