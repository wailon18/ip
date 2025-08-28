package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

/**
 * Represents the exit command in the Atlas application.
 * When executed, it signals the program to terminate.
 */
public class ByeCommand extends Command {

    /**
     * Does nothing on execution since only termination is needed.
     *
     * @param taskList the current list of tasks (unused)
     * @param atlasCLI the CLI for user interaction (unused)
     * @param atlasStorage the storage handler (unused)
     */
    @Override
    public void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage) {}

    /**
     * Indicates that this command should terminate the program.
     *
     * @return {@code true} always, since this is the exit command
     */

    @Override
    public boolean isExit() {
        return true;
    }
}
