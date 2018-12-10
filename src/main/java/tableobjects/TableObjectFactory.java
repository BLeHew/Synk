package tableobjects;

import connection.DBSource;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class TableObjectFactory {
    public static TableObject getTableObject(String type){
        switch (type){
            case "project": return new Project();
            case "task": return new Task();
            default: return null;
        }
    }
    public static TableObject getTableObject(String type, ResultSet rs) throws SQLException{
        switch (type){
            case "project": return new Project(rs);
            case "task": return new Task(rs);
            case "users": return new User(rs);
            default: return null;
        }
    }
}
