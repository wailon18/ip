package atlas.tasklist;

import atlas.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class AtlasTaskList {
    private List<Task> taskList;

    public AtlasTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }


    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }

    public Task getTaskByIndex(int index) {
        return this.taskList.get(index);
    }

    public Task removeTaskByIndex(int index) {
        return this.taskList.remove(index);
    }

    public List<Task> getTasksWithQuery(String query) {
        List<Task> tasks = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.toString().contains(query)) {
                tasks.add(task);
            }
        }
        return tasks;
    }
}
