package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Todo;

import java.io.IOException;

/**
 * Represents a command that adds a todo task to the task list.
 */
public class TodoCommand extends Command {

    private Todo todo;

    public TodoCommand(String task) {
        this.todo = new Todo(task);
    }

    /**
     * Executes the todo command.
     * Adds the todo task to the task list, shows a confirmation message,
     * and saves the updated task list to storage.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        taskList.addTask(this.todo);
        atlasCLI.printGotIt(this.todo);
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }
    }
}
