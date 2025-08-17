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

        echoUserCommands();

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
}
