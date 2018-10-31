package connection;


import java.sql.PreparedStatement;

public class Query {
    public static final String SELECTALL = "SELECT * FROM ";
    public static final String VERIFYCREDENTIALS = "CALL GetUserCredentials(";
    public static final String LASTID = "SELECT LAST_INSERT_ID()";
    public static final String USERTASKATTACHED = "CALL GetUsersAttachedToTask(";
    public static final String USERPROJATTACHED = "CALL GetUsersAttachedToTask(";
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
    public static String insertProject(String name,String description){
        return INSERTPROJECT + name + "," + description + ")";
    }
    public static String deleteTask(int taskId){
        return DELETETASK + taskId + ")";
    }
    public static String updateTask(String name, int taskId) {
        return UPDATETASK + name + "," + taskId  + ")";
    }

}
