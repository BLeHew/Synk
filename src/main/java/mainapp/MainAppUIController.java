package mainapp;

import connection.SynkConnection;
import javafx.collections.transformation.FilteredList;
import tableobjects.Task;
import tableobjects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class MainAppUIController {
    public static FilteredList<User> getUsersToDisplay(int projId){
        ResultSet rs;
        HashSet<Integer> userIdsToDisplay = new HashSet<>();
        try {
            PreparedStatement stmt = SynkConnection.con.prepareStatement(
                    "SELECT u.id FROM users u,user_proj_assigned upa " +
                            "WHERE u.id = upa.user_id AND upa.proj_id = " + projId);
            rs = stmt.executeQuery();
            while(rs.next()){
                userIdsToDisplay.add(rs.getInt("id"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        return AppData.getInstance().getUserItems().filtered(s -> userIdsToDisplay.contains(s.getUserId()));
    }
    public static FilteredList<User> getFilteredUsersToDisplay(int taskId){
        HashSet<Integer> userIdsToDisplay = new HashSet<>();
        try {
            ResultSet rs;
            //PreparedStatement stmt = SynkConnection.con.prepareStatement("CALL GetUsersAttachedToTask(?)");

            PreparedStatement stmt = SynkConnection.con.prepareStatement(
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
        }
        return AppData.getInstance().getUserItems().filtered(s -> userIdsToDisplay.contains(s.getUserId()));
    }
}
