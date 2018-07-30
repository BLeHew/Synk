package roles;

import tasks.Task;

public class IndivContributor extends User {
    public void updateTaskPercentage(Task task, double percent){
        task.updatePercentCompleted(percent);
    }
}
