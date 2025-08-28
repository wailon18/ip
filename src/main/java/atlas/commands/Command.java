package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

/**
 * Represents an executable command in the Atlas application.
 * Subclasses define specific actions and their execution logic.
 */
public abstract class Command {

    /**
     * Executes the command using the given task list, CLI, and storage.
     * Subclasses implement the specific behavior.
     *
     * @param taskList   the current list of tasks
     * @param atlasCLI   the CLI for user interaction
     * @param atlasStorage the storage handler for saving/loading tasks
     */
    public abstract void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage);

    /**
     * Indicates whether this command will terminate the program.
     * By default returns {@code false}; subclasses override if needed.
     *
     * @return {@code true} if the command should exit, {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
