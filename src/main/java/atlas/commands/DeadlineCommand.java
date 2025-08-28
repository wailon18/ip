package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Deadline;

import java.io.IOException;
import java.time.LocalDateTime;

public class DeadlineCommand extends Command{

    private Deadline deadline;

    public DeadlineCommand(String task, LocalDateTime deadline) {
        this.deadline = new Deadline(task, deadline);
    }


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
