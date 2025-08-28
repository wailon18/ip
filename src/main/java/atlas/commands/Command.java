package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

public abstract class Command {

    public abstract void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage);

    public boolean isExit() {
        return false;
    }
}
