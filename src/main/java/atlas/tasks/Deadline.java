package atlas.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String task, LocalDateTime deadline) {
        super(task);
        this.deadline = deadline;
    }

    @Override
    public String saveTask() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return "D|" + super.saveTask() + "|" + this.deadline.format(dtf);
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.deadline.format(dtf) + ")";
    }
}
