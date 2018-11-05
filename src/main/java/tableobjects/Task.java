package tableobjects;

import connection.DBSource;

import java.sql.*;

public class Task extends TableObject{
    private int projID;
    private String pctComplete;

    public Task(int id, String desc, String name, int projID, String pctComplete) {
        super(id,name,desc);
        super.type = "task";
        this.projID = projID;
        this.pctComplete = String.valueOf(Integer.parseInt(pctComplete) % 100);
    }
    public Task(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("description"),
                rs.getString("name"),
                rs.getInt("proj_id"),
                String.valueOf(rs.getInt("pctComplete")));
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
        if(pctComplete.length() < 5) {
            int i = Integer.parseInt(pctComplete);
            this.pctComplete = String.valueOf(Math.max(0, Math.min(i, 100)));
        }
    }
    public String toQuery(){
        return projID + ",'" + super.getName() + "','" + super.getDesc() + "'," + pctComplete;
    }
    public void insertIntoDB(){
        Connection conn = null;
        try {
            conn = DBSource.getConnection();
            String query = "INSERT INTO task VALUES(null," + toQuery() + ")";
            System.out.println(query);
            PreparedStatement s = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            if(rs.next()){ super.setId(rs.getInt(1)); }
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }


}

