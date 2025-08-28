package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

public class ByeCommand extends Command {

    @Override
    public void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage) {}

    @Override
    public boolean isExit() {
        return true;
    }
}
