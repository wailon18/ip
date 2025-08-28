package Commands;

import Cli.AtlasCli;
import Storage.AtlasStorage;
import TaskList.AtlasTaskList;
import Tasks.Task;

import java.io.IOException;

public class CompleteCommand extends Command {
    private int index;

    public CompleteCommand(int index){
        this.index = index;
    }

    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        Task task = taskList.getTaskByIndex(index);
        if (task.isComplete()) {
            atlasCLI.printAlreadyComplete();
        } else {
            task.toggleComplete();
            atlasCLI.printMarkAsComplete(task);
        }
        try {
            atlasStorage.save(taskList);
        } catch (IOException e) {
            atlasCLI.showError(e.getMessage());
        }

    }
}
