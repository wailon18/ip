package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Event;

import java.io.IOException;
import java.time.LocalDateTime;

public class EventCommand extends Command {

    private Event event;

    public EventCommand(String event, LocalDateTime startDate, LocalDateTime endDate) {
        this.event = new Event(event, startDate, endDate);
    }


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
