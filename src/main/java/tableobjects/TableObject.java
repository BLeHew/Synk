package tableobjects;

import connection.DBSource;

import java.sql.*;

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
    public String toQuery(){return "";}
    public void insertIntoDB(){
            Connection conn = null;
            try {
                conn = DBSource.getConnection();
                String query = "INSERT INTO " +  type + " VALUES(null," + toQuery() + ")";
                PreparedStatement s = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                s.executeUpdate();
                ResultSet rs = s.getGeneratedKeys();
                if(rs.next()){ id = (rs.getInt(1)); }
            }catch (SQLException s){
                s.printStackTrace();
            }finally { DBSource.close(conn); }
    }
    public void removeFromDB(){
        Connection conn = null;
        try {
            conn = DBSource.getConnection();
            conn.prepareStatement("DELETE FROM " +  type  + " WHERE id = " + id).executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }
}
