package login;

import connection.DBSource;
import login.credentials.CredentialsCheck;
import org.junit.Test;

import static org.junit.Assert.*;

public class CredentialsCheckTest {
    String username = "brice";
    String password = "Brice1@";
    String passwordReentry = "Brice1@";
    String email = "brice@brice.com";

    @Test
    public void success() {
        DBSource.establish();
        assertTrue(CredentialsCheck.success(username,password,passwordReentry,email));
        DBSource.con.close();
    }

    @Test
    public void getErrorType() {
    }

    @Test
    public void hasErrors() {
    }

    @Test
    public void validateEmail() {
    }

    @Test
    public void validatePassword() {
    }
}