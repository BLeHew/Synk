package login.credentials;

import login.credentials.credentialserrors.*;

import java.util.ArrayList;


public class CredentialChecker {

    private ArrayList<CredentialsError> errorsList = new ArrayList<>();

    private static String errorMessage = "SUCCESS";

    private static CredentialChecker instance;

    private CredentialChecker(){
        errorsList.add(new InvalidEmailError());
        errorsList.add(new PassDoesntMatchError());
        errorsList.add(new InvalidPasswordError());
        errorsList.add(new UserToShortError());
    }
    public static CredentialChecker getInstance(){
        if(instance == null){
            instance = new CredentialChecker();
        }
        return instance;
    }
    public String errorMessage(){
        return errorMessage;
    }
    public String check(String username,String password, String passwordReenter, String email){
        errorMessage = "SUCCESS";
        for(CredentialsError credentialsError : errorsList){
            if(!credentialsError.check(username,password,passwordReenter,email)){
                errorMessage = credentialsError.getErrorMessage();
                return errorMessage;
            }
        }
        return errorMessage;
    }


}
