package Tasks;

public abstract class Task {
    private String task;
    private boolean isComplete;

    public Task(String task) {
        this.task = task;
        this.isComplete = false;
    }

    public String saveTask() {
        if (this.isComplete) {
            return "1|" + this.task;
        }
        return "0|" + this.task;
    }

    public void toggleComplete() {
        this.isComplete = !this.isComplete;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public String toString() {
        String checkbox = this.isComplete ? "[X]" : "[ ]";
        return checkbox + " " + this.task;
    }
}
