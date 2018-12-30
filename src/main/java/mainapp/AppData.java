package mainapp;

import connection.Database;
import javafx.collections.ObservableList;
import tableobjects.TableObject;

public class AppData {
    public static void insertIntoDB(TableObject t){
        Database.runQuery("insert",t);
    }
    public static void removeFromDB(TableObject t){
        Database.runQuery("delete",t);
    }
    public static void updateDB(TableObject t){
        Database.runQuery("update",t);
    }
    public static ObservableList<TableObject> getUsersAttachedToProject(int projectId){
        return Database.getItems("users","CALL GetUsersAttachedToProject(" + projectId + ")");
    }
    public static ObservableList<TableObject> getUsersAttachedToTask(int taskId){
        return Database.getItems("users","CALL GetUsersAttachedToTask(" + taskId +")");
    }
    public static ObservableList<TableObject> getAll(String type){
        return Database.getItems(type,"SELECT * FROM " + type);
    }
    public static ObservableList<TableObject> searchUsersWithUsername(String username){
        return Database.getItems("users","SELECT * FROM users WHERE username LIKE '%" + username + "%'");
    }
    public static ObservableList<TableObject> getProjectTasks(int projId){
        return Database.getItems("task", "SELECT * FROM task where proj_id = " + projId);
    }
    /*
    Database.getItems("users", "CALL GetUsersAttachedToProject(" + project.getId() + ")")
    Database.getItems("users","CALL GetUsersAttachedToTask(" + t.getId() + ")")
    Database.getItems("users","SELECT * FROM users")
    Database.getItems("users","SELECT * FROM users WHERE username LIKE '%" + txtFieldSearch.getText() + "%'")

    Database.getItems("task", "SELECT * FROM task WHERE proj_id = " + project.getId())

    Database.getItems("project","SELECT * FROM project")
    Database.getItems("project","SELECT * FROM project")

    Database.insertAssignment(userId,projId,taskId))
     */
}
