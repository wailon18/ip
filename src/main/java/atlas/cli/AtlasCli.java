package atlas.cli;

import atlas.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interaction for the Atlas application.
 * Provides methods to reda input commands and display output
 * messages in the command-line interface.
 */
public final class AtlasCli {

    private final Scanner sc;
    private final List<Task> tasks = new ArrayList<>();

    public AtlasCli(Scanner sc) {
        this.sc = sc;
    }

    /**
     * Displays the welcome message and usage instructions to the user.
     */
    public void showWelcome() {
        String welcomeMessage = "Hello! I'm Atlas.Atlas\nWhat can I do for you?";
        String informationMessage = "Type commands in the following format:\n"
                + "- todo <task>\n- deadline <task> /by <datetime>\n- event <task> /from <datetime> /to <datetime>\n"
                + "Note that <datetime> must be in the form of <dd/MM/yyyy HHmm> where HHmm is time in 24-hour format\n";
        this.showLine();
        System.out.println(welcomeMessage);
        this.showLine();
        System.out.println(informationMessage);
        this.showLine();
    }

    /**
     * Reads the next line of user input from the command-line interface.
     * @return the raw input string entered by the user
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a horizontal separator line to the standard output.
     */
    public void showLine() {
        System.out.println("\n_____________________________________________________________________\n");
    }

    /**
     * Displays an error message to the standard output.
     *
     * @param error the error message to display
     */
    public void showError(String error) {
        System.out.println(error);
    }

    /**
     * Prints a farewell message and a separator line to indicate
     * the end of the program.
     */
    public void showGoodbye() {
        this.showLine();
        System.out.println("Bye. Hope to see you again soon!");
        this.showLine();
    }

    /**
     * Prints a confirmation message when a task is added,
     * displays the task, and shows the updated task count.
     *
     * @param toBeAdded the task that was just added
     */
    public void printGotIt(Task toBeAdded) {
        this.showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + toBeAdded);
        printNowYouHave();
        this.showLine();
    }

    /**
     * Prints the current number of tasks in the list,
     * handling singular and plural forms correctly.
     */
    public void printNowYouHave() {
        if (this.tasks.size() == 1) {
            System.out.println("Now you have " + this.tasks.size() + " task in the list.");
        } else {
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Prints a confirmation message when a task is removed,
     * displays the removed task, and shows the updated task count.
     *
     * @param toBeDeleted the task that was just deleted
     */
    public void printDeleteMessage(Task toBeDeleted) {
        this.showLine();
        System.out.println("Noted. I've removed this task:\n" + toBeDeleted);
        this.printNowYouHave();
        this.showLine();
    }

    /**
     * Prints a message indicating that the task is
     * marked as completed, followed by a separator line.
     */
    public void printAlreadyComplete() {
        System.out.println("This task has already been marked as completed");
        this.showLine();
    }

    /**
     * Prints a message indicating that the task
     * is not yet marked as completed, followed by a separator line.
     */
    public void printAlreadyIncomplete() {
        System.out.println("This task has not been marked as completed");
        this.showLine();
    }

    /**
     * Prints a confirmation message when a task is marked as complete,
     * displays the task, and surrounds the message with separator lines.
     *
     * @param toBeMarked the task that was marked as complete
     */
    public void printMarkAsComplete(Task toBeMarked) {
        this.showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + toBeMarked);
        this.showLine();
    }

    /**
     * Prints a confirmation message when a task is marked as incomplete,
     * displays the task, and surrounds the message with separator lines.
     *
     * @param toBeMarked the task that was marked as incomplete
     */
    public void printMarkAsIncomplete(Task toBeMarked) {
        this.showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("    " + toBeMarked);
        this.showLine();
    }

}
