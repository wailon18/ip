package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

/**
 * Represents a command that lists all tasks in the task list.
 */

public class ListCommand extends Command {

    /**
     * Executes the list command.
     * Displays all current tasks in the task list to the user,
     * surrounded by separator lines.
     *
     * @param taskList the current list of tasks
     * @param atlasCLI the CLI for user interaction
     * @param atlasStorage the storage handler (unused in this command)
     */
    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        atlasCLI.showLine();
        atlasCLI.listTasks(taskList.getTaskList());
        atlasCLI.showLine();
    }

    @Override
    public String executeToString(AtlasTaskList taskList, AtlasStorage atlasStorage) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        var tasks = taskList.getTaskList();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append('\n');
        }
        return sb.toString().trim();
    }
}
