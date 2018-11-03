package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


public class User implements TableObject {

    private int id;
    private String username;
    private String email;
    private int pass_hash;
    private int priv_level;

    public User(int id, String username, String email, int pass_hash, int priv_level) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pass_hash = pass_hash;
        this.priv_level = priv_level;
    }
    public User(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getInt("pass_hash"),
                rs.getInt("priv_lvl"));
    }
    @Override
    public String toString(){
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPass_hash() {
        return pass_hash;
    }

    public void setPass_hash(int pass_hash) {
        this.pass_hash = pass_hash;
    }

    public int getPriv_level() {
        return priv_level;
    }

    public void setPriv_level(int priv_level) {
        this.priv_level = priv_level;
    }
}
