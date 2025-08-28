package atlas.tasklist;

import atlas.tasks.Task;

import java.util.List;

public class AtlasTaskList {
    private List<Task> taskList;

    public AtlasTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void listTasks() {
        for (int i = 0; i < this.taskList.size(); i++) {
            System.out.println((i+1) + "." + this.taskList.get(i));
        }
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
}
