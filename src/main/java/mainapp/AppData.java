package mainapp;

import connection.DBSource;
import javafx.collections.ObservableList;
import tableobjects.TableObject;

public class AppData {
    public ObservableList<TableObject> getAll(String type){
        return DBSource.getItems(type,"SELECT * FROM " + type);
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
