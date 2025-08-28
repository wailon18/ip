package atlas.tasks;

public class Todo extends Task {
    public Todo(String task) {
        super(task);
    }

    @Override
    public String saveTask() {
        return "T|" + super.saveTask();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
