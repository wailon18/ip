public class Todo {
    private String task;
    private boolean completed;

    public Todo(String task) {
        this.task = task;
        this.completed = false;
    }

    public void markAsComplete() {
        this.completed = true;
    }

    public void markAsIncomplete() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String checkbox = this.completed ? "[X]" : "[ ]";
        return checkbox + " " + this.task;
    }
}
