package atlas.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String task, LocalDateTime startDate, LocalDateTime endDate) {
        super(task);
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public String saveTask() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return "E|" + super.saveTask() + "|" + this.startDate.format(dtf) + "|" + this.endDate.format(dtf);
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        return "[E]" + super.toString() + " (from: " + startDate.format(dtf) + " to: " + endDate.format(dtf) + ")";
    }

    public LocalDateTime getStart() {
        return startDate;
    }

    public LocalDateTime getEnd() {
        return endDate;
    }

}
