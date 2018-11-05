package tableobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Project extends TableObject {
    public Project(int id, String name, String desc){
        super(id,name,desc);
    }
    public Project(ResultSet rs) throws SQLException {
        this(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }
    public Project(){
        this(0,"New Project","No Description");
    }
}
