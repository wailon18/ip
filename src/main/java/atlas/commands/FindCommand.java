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

    @Override
    public String executeToString(AtlasTaskList taskList, AtlasStorage atlasStorage) {
        var matches = taskList.getTasksWithQuery(query);
        if (matches.isEmpty()) {
            return "No matching tasks found for: " + query;
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append('\n');
        }
        return sb.toString().trim();
    }
}
