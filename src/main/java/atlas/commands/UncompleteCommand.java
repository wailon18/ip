package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Task;

import java.io.IOException;

/**
 * Represents a command that marks a task as incomplete.
 * If the task is already incomplete, shows a warning instead.
 */

public class UncompleteCommand extends Command {
    private int index;
    public UncompleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the uncomplete command.
     * Marks the task at the given index as incomplete and saves the updated task list.
     * If the task is already incomplete, prints a warning message instead.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        Task task = taskList.getTaskByIndex(this.index);
        if (!task.isComplete()) {
            atlasCLI.printAlreadyIncomplete();
        } else {
            task.toggleComplete();
            atlasCLI.printMarkAsIncomplete(task);
        }
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }
    }
}
