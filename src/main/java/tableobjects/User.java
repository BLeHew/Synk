package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
