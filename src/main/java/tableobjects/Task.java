package tableobjects;

import connection.DBSource;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import userinterface.Toast;

import java.sql.*;

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
        if(pctComplete.length() < 4) {
            try {
                i = Integer.parseInt(pctComplete);
            }catch (NumberFormatException e){
                Toast.makeText("NOT A NUMBER");
                return;
            }
            if (i > 100){
                Toast.makeText("TOO BIG");
            }
            this.pctComplete = String.valueOf(Math.max(0, Math.min(i, 100)));
        }else {
            Toast.makeText("TOO BIG");
        }
    }
    public String toQuery(){
        return projID + ",'" + super.getName() + "','" + super.getDesc() + "'," + pctComplete;
    }


}

