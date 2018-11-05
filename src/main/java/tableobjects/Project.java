package tableobjects;

import connection.DBSource;

import java.sql.*;

public class Project extends TableObject {
    public Project(int id, String name, String desc){
        super(id,name,desc);
        super.type = "project";
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
            System.out.println(ps);
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }

}
