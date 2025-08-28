package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

public class ListCommand extends Command {


    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        atlasCLI.showLine();
        System.out.println("Here are the tasks in your list:");
        taskList.listTasks();
        atlasCLI.showLine();
    }
}
