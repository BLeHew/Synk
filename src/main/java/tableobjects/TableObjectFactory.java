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
            case "project": return getProjectWithTasks(rs);
            case "task": return new Task(rs);
            case "users": return new User(rs);
            default: return null;
        }
    }
    public static Project getProjectWithTasks(ResultSet rs) throws SQLException{
        Project project = new Project(rs);
        Connection conn = DBSource.getConnection();
        ResultSet resultSet = conn.prepareStatement("SELECT * FROM task WHERE proj_id = " + project.getId()).executeQuery();
        Set<Task> tasks = new HashSet<>();

        while(resultSet.next()){
            tasks.add(new Task(resultSet));
        }
        project.setTasks(tasks);

        resultSet = conn.prepareStatement("Call GetUsersAttachedToProject(" + project.getId() + ")").executeQuery();
        Set<User> users = new HashSet<>();

        while(resultSet.next()){
            users.add(new User(resultSet));
        }
        project.setUsers(users);

        DBSource.close(conn);
        return project;
    }
}
