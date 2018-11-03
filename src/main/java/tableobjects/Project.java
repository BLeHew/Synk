package tableobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Project implements TableObject {
    private int id;
    private String name;
    private String desc;

    public Project(int id, String name, String desc){
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
    public Project(ResultSet rs) throws SQLException {
        this(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }
    public Project(){
        this(0,"New Project","No Description");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString(){
        return name;
    }
}
