package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Project implements TableObject{
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty desc;

    public Project(int id, String name, String desc){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.desc = new SimpleStringProperty(desc);
    }
    public Project(ResultSet rs) throws SQLException{
        this(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }
    public Project(){
        this(0,"New Project","No Description");
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    @Override
    public String toString(){
        return name.get();
    }


}
