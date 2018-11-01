package tableobjects;

import javafx.beans.property.*;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Task implements TableObject {
    private final IntegerProperty id;
    private final StringProperty desc;
    private final StringProperty name;
    private final IntegerProperty projID;
    private final DoubleProperty pctComplete;

    public Task(int id, String desc, String name, int projID,double pctComplete) {
        this.id = new SimpleIntegerProperty(id);
        this.desc = new SimpleStringProperty(desc);
        this.name = new SimpleStringProperty(name);
        this.projID = new SimpleIntegerProperty(projID);
        this.pctComplete = new SimpleDoubleProperty(pctComplete % 100);
    }
    public Task(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("description"),
                rs.getString("name"),
                rs.getInt("proj_id"),
                rs.getDouble("pctComplete"));
    }
    public Task(){
        id = null;
        desc = null;
        name = null;
        projID = null;
        pctComplete = null;
    }
    /**
     * Constructs the default task object to be inserted into the database.
     */
    public Task(int projId){
        this(0,"No Description","New Task",projId,0);
    }

    public void setPctComplete(double pctComplete){
        this.pctComplete.setValue(pctComplete % 100);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public String getName() {
        return name.get();
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public void setProjID(int projID) {
        this.projID.set(projID);
    }

    public int getProjID() {
        return projID.get();
    }

    @Override
    public String toString() {
        return  name.get();
    }



}
