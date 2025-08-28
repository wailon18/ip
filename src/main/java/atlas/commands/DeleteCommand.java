package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

import java.io.IOException;

/**
 * Represents a command that deletes a task from the task list.
 */

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index){
        this.index = index;
    }

    /**
     * Executes the delete command.
     * Removes the task at the given index, shows a confirmation message,
     * and saves the updated task list to storage.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        atlasCLI.printDeleteMessage(taskList.removeTaskByIndex(index));
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }
    }
}
