package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private final IntegerProperty id;
    private final StringProperty username;

    public User(int id, String username) {
        this.id = new SimpleIntegerProperty(id);
        this.username =  new SimpleStringProperty(username);
    }
    public User(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),rs.getString("username"));
    }
    public User(){
        id = null;
        username = null;
    }
    public int getUserId(){
        return id.get();
    }
    @Override
    public String toString(){
        return username.get();
    }





}
