package login.credentials;

import login.credentials.credentialserrors.*;

import java.util.ArrayList;
import java.util.HashMap;


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
        HashMap<String,String> userItems = new HashMap<>();
        userItems.put("username",username);
        userItems.put("password",password);
        userItems.put("passwordReenter",passwordReenter);
        userItems.put("email",email);

        errorMessage = "SUCCESS";
        for(CredentialsError credentialsError : errorsList){
            if(!credentialsError.check(userItems)){
                errorMessage = credentialsError.getErrorMessage();
                return errorMessage;
            }
        }
        return errorMessage;
    }


}
