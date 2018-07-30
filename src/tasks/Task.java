package tasks;

import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private String id;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate lastUpdateDate;
    private double percentCompleted;

    public Task(String name, LocalDate startDate, LocalDate dueDate){
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public void updatePercentCompleted(double percent){
        percentCompleted = percent;
    }
    public String getId(){
        return id;
    }
}
