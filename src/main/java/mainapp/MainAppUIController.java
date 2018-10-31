package mainapp;

import connection.SynkConnection;
import javafx.collections.transformation.FilteredList;
import tableobjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class MainAppUIController {
    public static int selectedProjectId;
    public static int selectedTaskId;

    public static FilteredList<User> getUsersToDisplay(int projId){
        ResultSet rs;
        HashSet<Integer> userIdsToDisplay = new HashSet<>();
        Connection conn = null;
        try {
            conn = SynkConnection.con.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.id FROM users u,user_proj_assigned upa " +
                            "WHERE u.id = upa.user_id AND upa.proj_id = " + projId);
            rs = stmt.executeQuery();
            while(rs.next()){
                userIdsToDisplay.add(rs.getInt("id"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
        return AppData.getInstance().getUserItems().filtered(s -> userIdsToDisplay.contains(s.getUserId()));
    }
    public static FilteredList<User> getFilteredUsersToDisplay(int taskId){
        HashSet<Integer> userIdsToDisplay = new HashSet<>();
        Connection conn = null;
        try {
            ResultSet rs;
            //PreparedStatement stmt = SynkConnection.con.prepareStatement("CALL GetUsersAttachedToTask(?)");
            conn = SynkConnection.con.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT u.id,u.username\n" +
                            " FROM users u,user_task_assigned uta\n" +
                            " WHERE u.id = uta.user_id AND uta.task_id = ?");
            stmt.setInt(1,taskId);
            rs = stmt.executeQuery();
            while(rs.next()){
                userIdsToDisplay.add(rs.getInt("id"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException s) { s.printStackTrace(); }
        }
        return AppData.getInstance().getUserItems().filtered(s -> userIdsToDisplay.contains(s.getUserId()));
    }
}
