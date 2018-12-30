package login.credentials;

import connection.Database;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialCheckerTest {

    @BeforeAll
    public static void init(){
        Database.createConnection();
        assertTrue(Database.con.isRunning());
    }
    @AfterAll
    public static void end(){
        Database.con.close();
        assertTrue(Database.con.isClosed());
    }
    String username = "brice";
    String password = "1234";
    String passwordRetype = "1234";
    String email = "a@b.com";

    @Test
    public void checkIfAllCredentialsAreValid(){
        assertEquals("SUCCESS",CredentialChecker.getInstance().check(username,password,passwordRetype,email));
    }
}