package Commands;

import Cli.AtlasCli;
import Storage.AtlasStorage;
import TaskList.AtlasTaskList;

public abstract class Command {

    public abstract void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage);

    public boolean isExit() {
        return false;
    }
}
