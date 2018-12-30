import connection.Database;
import javafx.application.Application;
import userinterface.SynkApp;


public class Main {
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args){
       Database.createConnection();
       Application.launch(SynkApp.class,args);
    }
}

