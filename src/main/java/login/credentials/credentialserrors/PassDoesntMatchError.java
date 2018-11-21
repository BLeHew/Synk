package login.credentials.credentialserrors;

public class PassDoesntMatchError extends CredentialsError{
    @Override
    public boolean check(String username,String password, String passwordReenter, String email){
        if(!password.equals(passwordReenter)){
            super.errorMessage = "Passwords Must Match";
            return false;
        }
        return true;
    }
}
