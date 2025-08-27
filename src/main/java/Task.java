public abstract class Task {
    private String task;
    private boolean completed;

    public Task(String task) {
        this.task = task;
        this.completed = false;
    }

    public String saveTask() {
        if (this.completed) {
            return "1|" + this.task;
        }
        return "0|" + this.task;
    }

    public void markAsComplete() {
        System.out.println(AtlasCLI.PARTITION);
        if (this.completed) {
            System.out.println("This task has already been marked as completed");
        } else {
            System.out.println("Nice! I've marked this task as done:");
            this.completed = true;
            System.out.println(this);
        }
        System.out.println(AtlasCLI.PARTITION);
    }

    public void markAsIncomplete() {
        System.out.println(AtlasCLI.PARTITION);
        if (!this.completed) {
            System.out.println("This task has not been marked as completed");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
            this.completed = false;
            System.out.println(this);
        }
        System.out.println(AtlasCLI.PARTITION);
    }

    public void silentComplete() {
        this.completed = true;
    }
    public void silentIncomplete() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String checkbox = this.completed ? "[X]" : "[ ]";
        return checkbox + " " + this.task;
    }
}
