package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Deadline;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command that adds a deadline task to the task list.
 */
public class DeadlineCommand extends Command{
public class DeadlineCommand extends Command {

    private Deadline deadline;

    public DeadlineCommand(String task, LocalDateTime deadline) {
        this.deadline = new Deadline(task, deadline);
    }

    /**
     * Executes the deadline command.
     * Adds the deadline task to the task list, shows a confirmation message,
     * and saves the updated task list to storage.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        taskList.addTask(this.deadline);
        atlasCLI.printGotIt(this.deadline);
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }
    }
}
