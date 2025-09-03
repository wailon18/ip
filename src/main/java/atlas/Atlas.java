package atlas;

import atlas.cli.AtlasCli;
import atlas.commands.Command;
import atlas.parser.AtlasParser;
import atlas.storage.AtlasStorage;
import atlas.tasklist.AtlasTaskList;

import java.util.Scanner;

/**
 * Entry point of the Atlas application.
 * Initializes storage, CLI, and task list, then runs the main program loop.
 */
public class Atlas {

    private AtlasTaskList taskList;
    private AtlasCli atlasCli;
    private AtlasStorage atlasStorage;

    public Atlas() {                    // <-- add this
        this.atlasStorage = new AtlasStorage("atlas.txt");
        this.taskList = new AtlasTaskList(atlasStorage.load());
    }

    /**
     * Starts the Atlas application.
     * Initializes storage, CLI, and task list, then enters the command loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String logo = "   ===      ========   =            ===       =====\n" +
                "  =   =        =       =           =   =     =     =\n" +
                " =     =       =       =          =     =    =\n" +
                " =     =       =       =          =     =     =====\n" +
                " =======       =       =          =======          =\n" +
                " =     =       =       =          =     =    =     =\n" +
                " =     =       =       ========   =     =     =====\n";
        System.out.print(logo + "\n\n");

        AtlasStorage atlasStorage = new AtlasStorage("atlas.txt");
        AtlasCli atlasCli = new AtlasCli(new Scanner(System.in));
        AtlasTaskList atlasTaskList = new AtlasTaskList(atlasStorage.load());

        atlasCli.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand =  atlasCli.readCommand();
                atlasCli.showLine();
                Command c = AtlasParser.parse(fullCommand);
                assert c != null;
                c.execute(atlasTaskList, atlasCli, atlasStorage);
                isExit = c.isExit();
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                atlasCli.showError(e.getMessage());
            }
        }
        atlasCli.showGoodbye();
    }

    public String getResponse(String input) {
        try {
            Command c = AtlasParser.parse(input);
            return c.executeToString(taskList, atlasStorage);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
