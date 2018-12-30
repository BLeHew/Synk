package tableobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Task extends TableObject{
    private int projID;
    private String pctComplete;

    public Task(int id, String desc, String name, int projID, String pctComplete) {
        super(id,name,desc);
        super.type = "task";
        this.projID = projID;
        this.pctComplete = pctComplete;
    }
    public Task(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("description"),
                rs.getString("name"),
                rs.getInt("proj_id"),
                rs.getString("pctComplete"));
    }
    public Task(int projID){
        this();
        this.projID = projID;
    }
    public Task(){
        this(0,"No Description","New Task",0,"0");
    }
    public int getProjID() {
        return projID;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public String getPctComplete() {
        return pctComplete;
    }

    public void setPctComplete(String pctComplete) {
        int i;
        if(pctComplete.length() <= 3) {
            try {
                i = Integer.parseInt(pctComplete);
            }catch (NumberFormatException e){
                return;
            }
            this.pctComplete = String.valueOf(Math.max(0, Math.min(i, 100)));
        }
    }
    public String toQuery(){
        return projID + ",'" + super.getName() + "','" + super.getDesc() + "'," + pctComplete;
    }
    @Override
    public PreparedStatement update(Connection conn) throws SQLException{
        PreparedStatement statement = conn.prepareStatement("UPDATE " + type + " SET name = ?, description = ?, pctComplete = ? WHERE id = ?");
        statement.setString(1,super.getName());
        statement.setString(2,super.getDesc());
        statement.setString(3,pctComplete);
        statement.setInt(4,super.getId());
        return statement;
    }

}

