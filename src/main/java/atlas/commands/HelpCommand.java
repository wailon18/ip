package atlas.commands;

import atlas.cli.AtlasCli;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

public class HelpCommand extends Command {

    @Override
    public void execute(AtlasTaskList taskList, AtlasCli atlasCLI, AtlasStorage atlasStorage) {
        atlasCLI.printHelp();
    }

    @Override
    public String executeToString(AtlasTaskList taskList, AtlasStorage atlasStorage) {
        return "todo, deadline, event\n" +
                "list (or ls), delete (or del) \n" +
                "mark <index>, unmark <index>\n" +
                "find <keyword>\n" +
                "bye\n";
    }
}
