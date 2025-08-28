package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Event;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command that adds an event task to the task list.
 */

public class EventCommand extends Command {

    private Event event;

    public EventCommand(String event, LocalDateTime startDate, LocalDateTime endDate) {
        this.event = new Event(event, startDate, endDate);
    }

    /**
     * Executes the event command.
     * Adds the event task to the task list, shows a confirmation message,
     * and saves the updated task list to storage.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler for saving tasks
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        taskList.addTask(this.event);
        atlasCLI.printGotIt(this.event);
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }
    }
}
