package Commands;

import Cli.AtlasCli;
import Storage.AtlasStorage;
import TaskList.AtlasTaskList;
import Tasks.Todo;

import java.io.IOException;

public class TodoCommand extends Command {

    private Todo todo;

    public TodoCommand(String task) {
        this.todo = new Todo(task);
    }

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
