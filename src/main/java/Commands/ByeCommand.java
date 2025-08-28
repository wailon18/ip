package Commands;

import Cli.AtlasCli;
import Storage.AtlasStorage;
import TaskList.AtlasTaskList;

public class ByeCommand extends Command {

    @Override
    public void execute(AtlasTaskList taskList,
            AtlasCli atlasCLI, AtlasStorage atlasStorage) {}

    @Override
    public boolean isExit() {
        return true;
    }
}
