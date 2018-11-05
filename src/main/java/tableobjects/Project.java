package tableobjects;

import connection.DBSource;

import java.sql.*;

public class Project extends TableObject {
    public Project(int id, String name, String desc){
        super(id,name,desc);
        super.type = "project";
    }
    public Project(ResultSet rs) throws SQLException {
        this(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }
    public Project(){
        this(0,"New Project","No Description");
    }

    public String toQuery(){
        return super.getId() + ",'" + super.getName() + "','" + super.getDesc();
    }

}
