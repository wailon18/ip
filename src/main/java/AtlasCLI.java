import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class AtlasCLI {
    public static final String PARTITION = "\n_____________________________________________________________________\n";
    private static final String DEADLINE_DELIMITER = "/by";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private  Scanner sc;
    private List<Task> tasks = new ArrayList<>();
    public AtlasCLI(Scanner sc) {
        this.sc = sc;
    }

    public void run() {
        // welcome message
        String welcomeMessage = "Hello! I'm Atlas\nWhat can I do for you?";
        System.out.println(PARTITION + welcomeMessage + PARTITION);

        while (true) {
            String command = this.sc.nextLine().trim();
            try {
                String action = command.split(" ")[0];
                if (action.equalsIgnoreCase("bye")) break;
                else if (action.equalsIgnoreCase("list") || action.equalsIgnoreCase("ls")) this.listTasks();
                else if (action.equalsIgnoreCase("unmark")) markTaskAsIncomplete(command);
                else if (action.equalsIgnoreCase("mark")) markTaskAsComplete(command);
                else if (action.equalsIgnoreCase("todo")) createTodo(command);
                else if (action.equalsIgnoreCase("deadline")) createDeadline(command);
                else if (action.equalsIgnoreCase("event")) createEvent(command);
                else if (action.equalsIgnoreCase("delete")) deleteEvent(command);
                else System.out.println(PARTITION + "Unrecognised command" + PARTITION);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(PARTITION + "Sorry, the description of the task cannot be empty, please try again." + PARTITION);
            }


        }
        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(PARTITION + exitMessage + PARTITION);
    }

    private void listTasks() {
        System.out.println(PARTITION);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i+1) + "." + this.tasks.get(i));
        }
        System.out.println(PARTITION);
    }

    private void createTodo(String command) {
        String task = command.substring(5);
        Todo toBeAdded = new Todo(task);
        this.tasks.add(toBeAdded);
        printGotIt(toBeAdded);
    }

    private void createDeadline(String command) {
        String taskWithDeadline = command.substring(9);
        int splitIndex = taskWithDeadline.lastIndexOf(DEADLINE_DELIMITER);
        if (splitIndex < 0) {
            System.out.println("Invalid Deadline Task, must have '/by' flag");
            return;
        }
        String deadline = taskWithDeadline.substring(splitIndex + DEADLINE_DELIMITER.length() + 1);
        String task = taskWithDeadline.substring(0, splitIndex - 1);
        Deadline toBeAdded = new Deadline(task, deadline);
        this.tasks.add(toBeAdded);
        printGotIt(toBeAdded);
    }

    private void createEvent(String command) {
        String taskEvent = command.substring(6);
        int fromIndex = taskEvent.lastIndexOf(FROM_DELIMITER);
        int toIndex = taskEvent.lastIndexOf(TO_DELIMITER);
        if (fromIndex < 0 || toIndex < 0) {
            System.out.println("Invalid Event Task, must have '/from' and '/to' flags");
        }
        String task = taskEvent.substring(0, fromIndex - 1);
        String startDate = taskEvent.substring(fromIndex + FROM_DELIMITER.length() + 1, toIndex - 1);
        String endDate = taskEvent.substring(toIndex + TO_DELIMITER.length() +1);
        Event toBeAdded = new Event(task, startDate, endDate);
        this.tasks.add(toBeAdded);
        printGotIt(toBeAdded);
    }


    private void printGotIt(Task toBeAdded) {
        System.out.println(PARTITION);
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + toBeAdded);
        printNowYouHave();
        System.out.println(PARTITION);
    }

    private void printNowYouHave() {
        if (this.tasks.size() == 1) {
            System.out.println("Now you have " + this.tasks.size() + " task in the list.");
        } else {
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        }
    }

    private void markTaskAsIncomplete(String command) {
        String[] splitString = command.split(" ");
        if (splitString.length != 2) {
            System.out.println("Please check your command and try again.");
        } else {
            try {
                int index = Integer.parseInt(splitString[1]) - 1;
                Task task = this.tasks.get(index);
                task.markAsIncomplete();
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                System.out.println("Invalid index");
            }
        }
    }

    private void deleteEvent(String command) {
        String[] splitString = command.split(" ");
        if (splitString.length != 2) {
            System.out.println("Please check your command and try again.");
        } else {
            try {
                int index = Integer.parseInt(splitString[1]) - 1;
                var task = tasks.get(index);
                tasks.remove(index);
                System.out.println(PARTITION + "Noted. I've removed this task:\n" + task);
                printNowYouHave();
                System.out.print(PARTITION);
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                System.out.println("Invalid index");
            }
        }
    }

    private void markTaskAsComplete(String command) {
        String[] splitString = command.split(" ");
        if (splitString.length != 2) {
            System.out.println("Please check your command and try again.");
        } else {
            try {
                int index = Integer.parseInt(splitString[1]) - 1;
                Task task = this.tasks.get(index);
                task.markAsComplete();
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                System.out.println("Invalid index");
            }
        }
    }
}
