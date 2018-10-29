package tableobjects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

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

    public void setTaskName(String taskName){
        this.taskName.set(taskName);
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

    public static TextFieldListCell<Task> getCell(){
        TextFieldListCell<Task> cell = new TextFieldListCell<>();
        cell.setConverter(new StringConverter<Task>() {
            @Override
            public String toString(Task task) {
                return task.getTaskName();
            }
            @Override
            public Task fromString(String string) {
                Task task = cell.getItem();
                if (string.length() > 0) {
                    task.setTaskName(string);
                }
                return task;
            }
        });
        return cell;
    }



}
