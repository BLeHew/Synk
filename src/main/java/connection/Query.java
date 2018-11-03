package connection;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {
    public static final String SELECTALL = "SELECT * FROM ";
    public static final String VERIFYCREDENTIALS = "CALL GetUserCredentials(";
    public static final String LASTID = "SELECT LAST_INSERT_ID()";
    public static final String USERTASKATTACHED = "CALL GetUsersAttachedToTask(";
    public static final String USERPROJATTACHED = "CALL GetUsersAttachedToProject(";
    public static final String INSERTUSER = "CALL InsertUser(";
    public static final String INSERTTASK = "CALL InsertTask(";
    private static final String INSERTPROJECT = "CALL InsertProject(";
    public static final String DELETETASK = "CALL DeleteTask(";
    public static final String UPDATETASK = "CALL UpdateTask(";

    public static String selectAll(String type){
        return SELECTALL + type;
    }
    public static String getCredentials(String username){
        return VERIFYCREDENTIALS + "'" + username + "')";
    }
    public static String getLastId(){
        return LASTID;
    }
    public static String getUserTaskAttached(int taskId){
        return USERTASKATTACHED + taskId + ")";
    }
    public static String getUserProjAttached(int projId) { return USERPROJATTACHED + projId + ")";}
    public static String insertUser(String username, String email, int pass_hash,int priv_level){
        return INSERTUSER + username + "," + email + "," + pass_hash + "," + priv_level + ")";
    }
    public static String insertTask(String name, int projId, String description){
        return INSERTTASK + name + "," + projId + "," + description + ")";
    }
    public static String insertProject(){
        return "CALL InsertProject(?,?)";
    }

    public static String delete(String type, int id){
        return "DELETE FROM " + type + " WHERE id = " + id;
    }
    public static String updateTask(String name, int taskId) {
        return UPDATETASK + name + "," + taskId  + ")";
    }
    public static void setAndRunStatement(Project p,Connection conn) throws SQLException{
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO projects VALUES(null,?,?)");
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getDesc());
        stmt.executeUpdate();
    }

    public static String getProjsTasksAttached(int projId){
        return "SELECT * FROM tasks WHERE proj_id = " + projId;
    }

}
