package mainapp;

import connection.DBSource;
import javafx.collections.ObservableList;
import tableobjects.TableObject;

public class AppData {
    public static void insertIntoDB(TableObject t){
        DBSource.runQuery("insert",t);
    }
    public static void removeFromDB(TableObject t){
        DBSource.runQuery("delete",t);
    }
    /*
    DBSource.getItems("users", "CALL GetUsersAttachedToProject(" + project.getId() + ")")
    DBSource.getItems("users","CALL GetUsersAttachedToTask(" + t.getId() + ")")
    DBSource.getItems("users","SELECT * FROM users")
    DBSource.getItems("users","SELECT * FROM users WHERE username LIKE '%" + txtFieldSearch.getText() + "%'")

    DBSource.getItems("task", "SELECT * FROM task WHERE proj_id = " + project.getId())

    DBSource.getItems("project","SELECT * FROM project")
    DBSource.getItems("project","SELECT * FROM project")

    DBSource.insertAssignment(userId,projId,taskId))
     */
}
