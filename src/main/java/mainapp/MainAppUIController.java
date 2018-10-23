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
                    "SELECT * FROM users u,user_proj_assigned upa " +
                            "WHERE u.user_id = upa.user_id AND upa.proj_id = " + projId);
            rs = stmt.executeQuery();
            while(rs.next()){
                userIdsToDisplay.add(rs.getInt("user_id"));
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        return AppData.getInstance().getUserItems().filtered(s -> userIdsToDisplay.contains(s.getUserID()));
    }
    public static FilteredList<Task> getFilteredTasksToDisplay(int projId){
        return AppData.getInstance().getTaskItems().filtered(s->s.getProjID() == projId);
    }
}
