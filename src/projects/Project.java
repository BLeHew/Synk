package projects;

import roles.ProjManager;
import tasks.Task;

import java.util.HashMap;

public class Project {
    private String name;
    private String description;
    private ProjManager owner;
    private HashMap<String, Task> tasks;

    public Project(String name, String description, ProjManager owner){
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Project(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void assignOwner(ProjManager owner){
        this.owner = owner;
    }

    public boolean addTask(Task task){
        return tasks.put(task.getId(), task) != null;
    }
}
