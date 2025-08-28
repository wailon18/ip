package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Task;

import java.io.IOException;

public class UncompleteCommand extends Command {
    private int index;
    public UncompleteCommand(int index) {
        this.index = index;
    }

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
