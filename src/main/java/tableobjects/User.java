package tableobjects;

import java.sql.ResultSet;
import java.sql.SQLException;


public class User extends TableObject {

    //private String username;
    private String email;
    private int pass_hash;
    private int priv_level;

    public User(int id, String username, String email, int pass_hash, int priv_level) {
        super(id,username,"");
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
