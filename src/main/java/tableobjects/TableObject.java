package tableobjects;

import connection.DBSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableObject implements Transactionable{
    protected String type;
    private int id;
    private String name;
    private String description;

    public TableObject(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDesc(){
        return description;
    }
    public void setDesc(String desc){
        this.description = desc;
    }
    public String toString(){
        return name;
    }
    public void insertIntoDB(){}
    public void removeFromDB(){
        Connection conn = null;
        try {
            conn = DBSource.con.getConnection();
            conn.prepareStatement("DELETE FROM " +  type  + " WHERE id = " + id).executeUpdate();
            System.out.println("DELETE FROM " +  type  + " WHERE id = " + id);
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }

}
