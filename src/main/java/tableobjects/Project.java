package tableobjects;

import connection.DBSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Project extends TableObject {

    private Set<Task> tasks = new HashSet<>();
    private Set<User> users = new HashSet<>();

    public Project(int id, String name, String desc){
        super(id,name,desc);
        super.type = "project";
    }
    public Set<User> getUsers(){
        return users;
    }
    public void setUsers(Set<User> users){
        this.users = users;
    }
    public void removeTask(Task t){
        tasks.remove(t);
    }
    public Set<Task> getTasks(){
        return tasks;
    }
    public void setTasks(Set<Task> tasks){
        this.tasks = tasks;
    }
    public Project(ResultSet rs) throws SQLException {
        this(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }
    public Project(){
        this(0,"New Project","No Description");
    }

    public String toQuery(){
        return "'" + super.getName() + "','" + super.getDesc() + "'";
    }

    @Override
    public void updateDB(){
        Connection conn = null;
        try {
            conn = DBSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE project SET name = ?,description = ? WHERE id = ?");
            ps.setString(1,super.getName());
            ps.setString(2,super.getDesc());
            ps.setInt(3,super.getId());
            ps.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }

}
