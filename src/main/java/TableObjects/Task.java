package TableObjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final IntegerProperty taskID;
    private final StringProperty taskDesc;
    private final StringProperty taskName;
    private final IntegerProperty projID;

    public Task(int taskID, String taskDesc, String taskName, int projID) {
        this.taskID = new SimpleIntegerProperty(taskID);
        this.taskDesc = new SimpleStringProperty(taskDesc);
        this.taskName = new SimpleStringProperty(taskName);
        this.projID = new SimpleIntegerProperty(projID);
    }

    public int getTaskID() {
        return taskID.get();
    }

    public IntegerProperty taskIDProperty() {
        return taskID;
    }

    public String getTaskDesc() {
        return taskDesc.get();
    }

    public StringProperty taskDescProperty() {
        return taskDesc;
    }

    public String getTaskName() {
        return taskName.get();
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }

    public int getProjID() {
        return projID.get();
    }

    public IntegerProperty projIDProperty() {
        return projID;
    }

    @Override
    public String toString(){
        return taskName.get();
    }




}
