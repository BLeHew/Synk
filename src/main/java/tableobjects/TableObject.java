package tableobjects;

import connection.DBSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.Objects;

public class TableObject{
    protected String type;
    private int id;
    private StringProperty name;
    private StringProperty description;

    public TableObject(int id, String name, String description){
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }
    public String getType(){
        return type;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public StringProperty nameProperty(){
        return name;
    }
    public StringProperty descriptionProperty(){
        return description;
    }
    public String getDesc(){
        return description.get();
    }
    public void setDesc(String desc){
        this.description.set(desc);
    }
    public String toString(){
        return name.get();
    }
    public String toQuery(){return "";}
    public void updateDB(){}
    /*
    public void insertIntoDB(){
        Connection conn = null;
        try {
            conn = DBSource.getConnection();
            String query = "INSERT INTO " +  type + " VALUES(null," + toQuery() + ")";
            PreparedStatement s = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            if(rs.next()){ id = rs.getInt(1); }
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }
    */
    public PreparedStatement insert(Connection conn) throws SQLException{
        String query = "INSERT INTO " +  type + " VALUES(null," + toQuery() + ")";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        return stmt;
    }
    public PreparedStatement update(Connection conn) throws SQLException{
        PreparedStatement statement = conn.prepareStatement("UPDATE " + type + " SET name = ?, description = ? WHERE id = ?");
        statement.setString(1,name.get());
        statement.setString(2,description.get());
        statement.setInt(3,id);
        return statement;
    }
    public PreparedStatement delete(Connection conn) throws SQLException{
        PreparedStatement statement = conn.prepareStatement("DELETE FROM " + type + " WHERE id = ?");
        statement.setInt(1,id);
        return statement;
    }
    /*
    public void removeFromDB(){
        Connection conn = null;
        try {
            conn = DBSource.getConnection();
            conn.prepareStatement("DELETE FROM " +  type  + " WHERE id = " + id).executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally { DBSource.close(conn); }
    }
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableObject that = (TableObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
