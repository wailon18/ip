import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
    private static final Map<String, Actions> ACTION_MAP = new HashMap<>();
    static {
        for (Actions a : Actions.values()) ACTION_MAP.put(a.name(), a);
        ACTION_MAP.put("LS", Actions.LIST);
        ACTION_MAP.put("DEL", Actions.DELETE);
    }

    private static Actions toAction(String token) {
        String key = token.trim().toUpperCase();
        Actions a = ACTION_MAP.get(key);
        if (a == null) throw new IllegalArgumentException("Unknown action: " + token);
        return a;
    }


    public void run() {
        // welcome message
        String welcomeMessage = "Hello! I'm Atlas\nWhat can I do for you?";
        String informationMessage = "Type commands in the following format:\n"
                + "- todo <task>\n- deadline <task> /by <datetime>\n- event <task> /from <datetime> /to <datetime>\n"
                + "Note that <datetime> must be in the form of <dd/MM/yyyy HHmm> where HHmm is time in 24-hour format\n";
        System.out.println(PARTITION + welcomeMessage + PARTITION + informationMessage + PARTITION);
        boolean running = true;
        this.loadTasks();

        while (running) {
            String command = this.sc.nextLine().trim();
            String actionStr = command.split(" ")[0];
            try {
                Actions action = toAction(actionStr);
                switch (action) {
                    case BYE:
                        running = false;
                        break;
                    case LIST:
                        this.listTasks();
                        break;
                    case UNMARK:
                        markTaskAsIncomplete(command);
                        break;
                    case MARK:
                        markTaskAsComplete(command);
                        break;
                    case TODO:
                        createTodo(command);
                        break;
                    case DEADLINE:
                        createDeadline(command);
                        break;
                    case EVENT:
                        createEvent(command);
                        break;
                    case DELETE:
                        deleteTask(command);
                        break;
                }
                saveTasks();
            } catch (IllegalArgumentException e) {
                System.out.println("Unrecognised command");
                System.out.println();
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Sorry, the description of the task cannot be empty, please try again.");
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
        String deadlineString = taskWithDeadline.substring(splitIndex + DEADLINE_DELIMITER.length() + 1);
        LocalDateTime deadline = this.parseDateTime(deadlineString);
        if (deadline == null) return;
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
        String startDateString = taskEvent.substring(fromIndex + FROM_DELIMITER.length() + 1, toIndex - 1);
        String endDateString = taskEvent.substring(toIndex + TO_DELIMITER.length() +1);
        LocalDateTime startDate = this.parseDateTime(startDateString);
        LocalDateTime endDate = this.parseDateTime(endDateString);
        if (startDate == null || endDate == null) return;
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
                System.out.println(PARTITION + "Invalid index" + PARTITION);
            }
        }
    }

    private void deleteTask(String command) {
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
                System.out.println(PARTITION + "Invalid index" + PARTITION);
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
                System.out.println(PARTITION + "Invalid index" + PARTITION);
            }
        }
    }

    private void saveTasks() {
        String fileName = "atlas.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, false))) {
            for (Task task : this.tasks) {
                out.println(task.saveTask());
            }
        } catch (IOException ex) {
            System.out.println("Error saving tasks: " + ex.getMessage());
        }
    }


    private void loadTasks() {
        File file = new File("atlas.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitString = line.split("\\|");
                String type = splitString[0];
                Task newTask;
                if (type.equals("T")) {
                    newTask = new Todo(splitString[2]);
                } else if (type.equals("D")) {
                    LocalDateTime deadline = parseDateTime(splitString[3]);
                    if (deadline == null) return;
                    newTask = new Deadline(splitString[2], deadline);
                } else if (type.equals("E")) {
                    LocalDateTime startDate = parseDateTime(splitString[3]);
                    LocalDateTime endDate = parseDateTime(splitString[4]);
                    if (startDate == null) return;
                    newTask = new Event(splitString[2], startDate, endDate);
                } else {
                    throw new IllegalArgumentException("Invalid task type: " + type);
                }
                if (splitString[1].equals("0")) {
                    newTask.silentIncomplete();
                } else {
                    newTask.silentComplete();
                }
                this.tasks.add(newTask);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            System.out.println("Error loading tasks: " + ex.getMessage());
        }
    }

    private LocalDateTime parseDateTime(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        try {
            return LocalDateTime.parse(date, dtf);
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid datetime format: " + ex.getMessage());
            System.out.println("Please try again.");
            return null;
        }
    }
}
