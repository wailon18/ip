import Cli.AtlasCli;
import Commands.Command;
import Parser.AtlasParser;
import Storage.AtlasStorage;
import TaskList.AtlasTaskList;

import java.util.Scanner;

public class Atlas {

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
}
