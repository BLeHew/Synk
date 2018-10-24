package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class Project {
    private final IntegerProperty projId;
    private final StringProperty projName;
    private final StringProperty projDesc;
    //private ObservableList<Task> projTasks = FXCollections.observableArrayList();

    public Project(int projId, String projName, String projDesc){
        this.projId = new SimpleIntegerProperty(projId);
        this.projName = new SimpleStringProperty(projName);
        this.projDesc = new SimpleStringProperty(projDesc);
    }
    @Override
    public String toString(){
        return projName.get();
    }

    public static TextFieldListCell<Project> getCell(){
        TextFieldListCell<Project> cell = new TextFieldListCell<>();
        cell.setConverter(new StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project.getProjName();
            }
            @Override
            public Project fromString(String string) {
                Project proj = cell.getItem();
                proj.setProjName(string);
                return proj ;
            }
        });
        return cell;
    }

    public int getProjId() {
        return projId.get();
    }

    public IntegerProperty projIdProperty() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId.set(projId);
    }

    public String getProjName() {
        return projName.get();
    }

    public StringProperty projNameProperty() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName.set(projName);
    }

    public String getProjDesc() {
        return projDesc.get();
    }

    public StringProperty projDescProperty() {
        return projDesc;
    }

    public void setProjDesc(String projDesc) {
        this.projDesc.set(projDesc);
    }
}
