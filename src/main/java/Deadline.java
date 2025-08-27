public class Deadline extends Task {
    private String deadline;
    public Deadline(String task, String deadline) {
        super(task);
        this.deadline = deadline;
    }

    @Override
    public String saveTask() {
        return "D|" + super.saveTask() + "|" + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
