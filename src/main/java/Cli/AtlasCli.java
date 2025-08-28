package Cli;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class AtlasCli {


    private final Scanner sc;
    private List<Task> tasks = new ArrayList<>();

    public AtlasCli(Scanner sc) {
        this.sc = sc;
    }

    public void showWelcome() {
        String welcomeMessage = "Hello! I'm Atlas\nWhat can I do for you?";
        String informationMessage = "Type commands in the following format:\n"
                + "- todo <task>\n- deadline <task> /by <datetime>\n- event <task> /from <datetime> /to <datetime>\n"
                + "Note that <datetime> must be in the form of <dd/MM/yyyy HHmm> where HHmm is time in 24-hour format\n";
        this.showLine();
        System.out.println(welcomeMessage);
        this.showLine();
        System.out.println(informationMessage);
        this.showLine();
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showLine() {
        System.out.println("\n_____________________________________________________________________\n");
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public void showGoodbye() {
        this.showLine();
        System.out.println("Bye. Hope to see you again soon!");
        this.showLine();
    }

    public void printGotIt(Task toBeAdded) {
        this.showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + toBeAdded);
        printNowYouHave();
        this.showLine();
    }

    public void printNowYouHave() {
        if (this.tasks.size() == 1) {
            System.out.println("Now you have " + this.tasks.size() + " task in the list.");
        } else {
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        }
    }

    public void printDeleteMessage(Task toBeDeleted) {
        this.showLine();
        System.out.println("Noted. I've removed this task:\n" + toBeDeleted);
        this.printNowYouHave();
        this.showLine();
    }

    public void printAlreadyComplete() {
        System.out.println("This task has already been marked as completed");
        this.showLine();
    }

    public void printAlreadyIncomplete() {
        System.out.println("This task has not been marked as completed");
        this.showLine();
    }

    public void printMarkAsComplete(Task toBeMarked) {
        this.showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + toBeMarked);
        this.showLine();
    }

    public void printMarkAsIncomplete(Task toBeMarked) {
        this.showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("    " + toBeMarked);
        this.showLine();
    }

}
