package connection;

public class Query {
    private String content;
    public static final String SELECTALL = "SELECT * FROM ";
    public static final String VERIFYCREDENTIALS = "SELECT username,pass_hash FROM users WHERE username = ";
    public static final String LASTID = "SELECT LAST_INSERT_ID()";
    public static final String USERTASKATTACHED = "CALL GetUsersAttachedToTask(?)";
    public static final String USERPROJATTACHED = "CALL GetUsersAttachedToTask(?)";
    

    public String selectAllType(String type) {
        return SELECTALL + type;
    }
    public String verifyCredentials(String username) {
        return VERIFYCREDENTIALS + username;
    }
    
/*
"SELECT * FROM projects"
"SELECT * FROM tasks"
"SELECT * FROM users"
"SELECT LAST_INSERT_ID()"


"SELECT username,pass_hash FROM users WHERE username = ?"
"SELECT username FROM users WHERE username = '" + userName + "'"


CALL GetUsersAttachedToTask(?)
CALL GetUsersAttachedToProject(?)

"INSERT INTO users VALUES (null,'" + userName + "','" + email + "'," + password.hashCode() + ",1)"
"INSERT INTO tasks VALUES(null,?,?,?)"

"DELETE FROM tasks WHERE id = " + task.getId()).executeUpdate();
"UPDATE " + type + " SET name = ? WHERE id = " + id

                            
 */
}
