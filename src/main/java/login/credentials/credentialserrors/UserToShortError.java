package login.credentials.credentialserrors;

public class UserToShortError extends CredentialsError{
    @Override
    public boolean check(String username,String password, String passwordReenter, String email){
        if(username.length() < 3){
            super.errorMessage = "Username must be longer than 3 characters";
            return false;
        }
        return true;
    }
}
