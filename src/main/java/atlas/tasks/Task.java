package atlas.tasks;

/**
 * Represents a generic task in the Atlas application.
 * Stores a task description and its completion status,
 * and provides common behaviors for all task types.
 */
public abstract class Task {
    private String task;
    private boolean isComplete;

    public Task(String task) {
        this.task = task;
        this.isComplete = false;
    }

    /**
     * Returns a string representation of the task for saving to storage.
     * Format: "1|description" if completed, "0|description" otherwise.
     *
     * @return the encoded string representation of the task
     */
    public String saveTask() {
        if (this.isComplete) {
            return "1|" + this.task;
        }
        return "0|" + this.task;
    }

    /**
     * Toggles the completion status of the task.
     * Marks it as complete if it was incomplete, or vice versa.
     */
    public void toggleComplete() {
        this.isComplete = !this.isComplete;
    }

    /**
     * Returns whether the task is marked as complete.
     *
     * @return {@code true} if the task is complete, {@code false} otherwise
     */
    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public String toString() {
        String checkbox = this.isComplete ? "[X]" : "[ ]";
        return checkbox + " " + this.task;
    }
}
