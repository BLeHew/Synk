package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final IntegerProperty userID;
    private final StringProperty username;

    public User(int userID, String username) {
        this.userID = new SimpleIntegerProperty(userID);
        this.username =  new SimpleStringProperty(username);
    }
    public int getUserID(){
        return userID.get();
    }
    @Override
    public String toString(){
        return username.get();
    }





}
