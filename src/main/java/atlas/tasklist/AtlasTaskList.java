package atlas.tasklist;

import atlas.tasks.Task;

import java.util.List;

/**
 * Represents a list of tasks in the Atlas application.
 * Provides methods to add, remove, retrieve, and display tasks.
 */
public class AtlasTaskList {
    private List<Task> taskList;

    public AtlasTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Prints all tasks in the list with an index.
     */
    public void listTasks() {
        for (int i = 0; i < this.taskList.size(); i++) {
            System.out.println((i+1) + "." + this.taskList.get(i));
        }
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Returns the full list of tasks.
     *
     * @return the list of tasks
     */
    public List<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Returns the task at the specified index in the list.
     *
     * @param index the position of the task to retrieved (0-based indexing)
     * @return the task at the given index
     */
    public Task getTaskByIndex(int index) {
        return this.taskList.get(index);
    }

    /**
     * Removes and returns the task at the specified index in the list.
     *
     * @param index the position of the task to remove (0-based indexing)
     * @return the removed task
     */
    public Task removeTaskByIndex(int index) {
        return this.taskList.remove(index);
    }
}
