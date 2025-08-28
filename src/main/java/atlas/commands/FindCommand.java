package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

public class FindCommand extends Command {

    String query;

    public FindCommand(String query) {
        this.query = query;
    }

    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        atlasCLI.listTasks(taskList.getTasksWithQuery(query));
    }
}
