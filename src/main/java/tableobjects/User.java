package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


public class User implements TableObject {

    private final IntegerProperty id;
    private final StringProperty username;
    private final StringProperty email;
    private final IntegerProperty pass_hash;
    private final IntegerProperty priv_level;

    public User(int id, String username,String email, int pass_hash,int priv_level) {
        this.id = new SimpleIntegerProperty(id);
        this.username =  new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.pass_hash = new SimpleIntegerProperty(pass_hash);
        this.priv_level = new SimpleIntegerProperty(priv_level);
    }
    public User(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("username"),
                null,//rs.getString("email"),
                0,//rs.getInt("pass_hash"),
                0);//rs.getInt("priv_level"));
    }
    public User(){
        id = null;
        username = null;
        email = null;
        pass_hash = null;
        priv_level = null;
    }
    public int getUserId(){
        return id.get();
    }
    @Override
    public String toString(){
        return username.get();
    }





}
