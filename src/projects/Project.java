package projects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {
    private final IntegerProperty projId;
    private final StringProperty projName;
    private final StringProperty projDesc;

    public Project(int projId, String projName, String projDesc){
        this.projId = new SimpleIntegerProperty(projId);
        this.projName = new SimpleStringProperty(projName);
        this.projDesc = new SimpleStringProperty(projDesc);
    }
    @Override
    public String toString(){
        return projName.get();
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
