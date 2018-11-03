package tableobjects;

import javafx.beans.property.*;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Task implements TableObject {
    private int id;
    private String desc;
    private String name;
    private int projID;
    private String pctComplete;

    public Task(int id, String desc, String name, int projID, String pctComplete) {
        this.id = id;
        this.desc = desc;
        this.name = name;
        this.projID = projID;
        this.pctComplete = String.valueOf(Integer.parseInt(pctComplete) % 100);
    }
    public Task(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("description"),
                rs.getString("name"),
                0,//rs.getInt("proj_id"),
                "0");//rs.getDouble("pctComplete"));
    }
    public Task(int projID){
        this();
        this.projID = projID;
    }
    public Task(){
        this(0,"No Description","New Task",0,"0");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Override
    public String toString(){
        return name;
    }

}

