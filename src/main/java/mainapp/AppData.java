package mainapp;

import connection.DBSource;
import tableobjects.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppData {

    public static Task addBlankTask(int projId){
        Task task = new Task(projId);
        DBSource.insertNewTask(task);
        task.setId(DBSource.getLastInsertId());
        return task;
    }

    public static void remove(Task task){
        Connection conn = null;
        try {
            conn = DBSource.con.getConnection();
            conn.prepareStatement("DELETE FROM tasks WHERE id = " + task.getId()).executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            DBSource.close(conn);}
    }
    public static void updateDatabase(String type,int id,String newValue){
        Connection conn = null;
        try {
            conn = DBSource.con.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + type + " SET name = ? WHERE id = " + id);
            stmt.setString(1,newValue);;
            stmt.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            DBSource.close(conn); }
    }
}
