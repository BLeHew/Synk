package tableobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableObjectFactory {
    public static TableObject getTableObject(String type){
        switch (type){
            case "project": return new Project();
            case "task": return new Task();
            case "user": return new User();
            default: return null;
        }
    }
    public static TableObject getTableObject(String type, ResultSet rs) throws SQLException{
        switch (type){
            case "project": return new Project(rs);
            case "task": return new Task(rs);
            case "user": return new User(rs);
            default: return null;
        }
    }
}
