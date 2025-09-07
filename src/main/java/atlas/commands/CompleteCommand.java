package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Task;

import java.io.IOException;

/**
 * Represents a command that marks a task as complete.
 * If the task is already complete, shows a warning instead.
 */
public class CompleteCommand extends Command {
    private int index;

    public CompleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the complete command.
     * Marks the task at the given index as complete and saves the updated task list.
     * If the task is already complete, prints a warning message instead.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        Task task = taskList.getTaskByIndex(index);
        if (task.isComplete()) {
            atlasCLI.printAlreadyComplete();
        }
        task.toggleComplete();
        atlasCLI.printMarkAsComplete(task);
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }

    }

    @Override
    public String executeToString(AtlasTaskList taskList, AtlasStorage atlasStorage) {
        Task task = taskList.getTaskByIndex(index);
        if (task.isComplete()) {
            return "This task has already been marked as completed";
        }
        task.toggleComplete();
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            return e.getMessage();
        }
        return "Nice! I've marked this task as done:\n" + task;
    }
}
