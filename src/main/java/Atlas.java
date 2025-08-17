import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Atlas {

    private static final String PARTITION = "\n_____________________________________________________________________\n";

    public static void main(String[] args) {
        String logo = "   ===      ========   =            ===       =====  \n" +
                "  =   =        =       =           =   =     =     = \n" +
                " =     =       =       =          =     =    =       \n" +
                " =     =       =       =          =     =     =====  \n" +
                " =======       =       =          =======          = \n" +
                " =     =       =       =          =     =    =     = \n" +
                " =     =       =       ========   =     =     =====  \n";
        System.out.println(logo);

        String welcomeMessage = "Hello! I'm Atlas\nWhat can I do for you?";
        System.out.println(PARTITION + welcomeMessage + PARTITION);

//        echoUserCommands();

        addAndList();

        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(PARTITION + exitMessage + PARTITION);
    }

    private static void echoUserCommands() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.next();
            if (command.equalsIgnoreCase("bye"))
                break;
            System.out.println(PARTITION + command + PARTITION);
        }
    }

    private static void addAndList() {
        Scanner sc = new Scanner(System.in);
        List<String> todoList = new ArrayList<>();
        while (true) {
            String command = sc.nextLine().trim();
            if (command.equalsIgnoreCase("list")) {
                System.out.print(PARTITION);
                for (int i = 0; i < todoList.size(); i++) {
                    System.out.println((i+1) + ". "
                            + todoList.get(i));
                }
                System.out.print(PARTITION);
            } else if (command.equalsIgnoreCase("bye")) {
                break;
            } else {
                todoList.add(command);
                System.out.println(PARTITION + "added: " + command + PARTITION);
            }
        }
    }
}
