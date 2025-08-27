public class Event extends Task {
    private String startDate;
    private String endDate;
    public Event(String task, String startDate, String endDate) {
        super(task);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }

    @Override
    public String saveTask() {
        return "E|" + super.saveTask() + "|" + this.startDate + "|" + this.endDate;
    }
}
