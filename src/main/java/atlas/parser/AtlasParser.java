package atlas.parser;

import atlas.commands.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AtlasParser {
    private static final Map<String, Actions> ACTION_MAP = new HashMap<>();
    private static final String DEADLINE_DELIMITER = "/by";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";

    static {
        for (Actions a : Actions.values()) ACTION_MAP.put(a.name(), a);
        ACTION_MAP.put("LS", Actions.LIST);
        ACTION_MAP.put("DEL", Actions.DELETE);
    }

    public static LocalDateTime parseDateTime(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        try {
            return LocalDateTime.parse(date, dtf);
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid datetime format: " + ex.getMessage());
            System.out.println("Please try again.");
            return null;
        }
    }

    public static Actions toAction(String token) {
        String key = token.trim().toUpperCase();
        Actions a = ACTION_MAP.get(key);
        if (a == null) throw new IllegalArgumentException("Unknown action: " + token);
        return a;
    }

    public static Command parse(String command) throws IndexOutOfBoundsException, IllegalArgumentException {
        command = command.trim();
        String actionStr = command.split(" ")[0];
        Actions action = AtlasParser.toAction(actionStr);
        switch (action) {
            case BYE:
                return new ByeCommand();
            case LIST:
                return new ListCommand();
            case UNMARK:
                String[] splitStringUnmark = command.split(" ");
                if (splitStringUnmark.length != 2) {
                    throw new IllegalArgumentException("Index is required.");
                } else {
                    int index = Integer.parseInt(splitStringUnmark[1]) - 1;
                    return new UncompleteCommand(index);
                }
            case MARK:
                String[] splitStringMark = command.split(" ");
                if (splitStringMark.length != 2) {
                    throw new IllegalArgumentException("Index is required.");
                } else {
                    int index = Integer.parseInt(splitStringMark[1]) - 1;
                    return new CompleteCommand(index);
                }
            case TODO:
                String task = command.substring(5);
                return new TodoCommand(task);
            case DEADLINE:
                String taskWithDeadline = command.substring(9);
                int splitIndex = taskWithDeadline.lastIndexOf(DEADLINE_DELIMITER);
                if (splitIndex < 0) {
                    throw new IllegalArgumentException("Invalid Deadline task, must have '/by' flag");
                }
                String deadlineString = taskWithDeadline.substring(splitIndex + DEADLINE_DELIMITER.length() + 1);
                LocalDateTime deadline = parseDateTime(deadlineString);
                if (deadline == null) {
                    throw new IllegalArgumentException("Datetime must not be null.");
                }
                String deadlineTask = taskWithDeadline.substring(0, splitIndex - 1);
                return new DeadlineCommand(deadlineTask, deadline);
            case EVENT:
                String taskEvent = command.substring(6);
                int fromIndex = taskEvent.lastIndexOf(FROM_DELIMITER);
                int toIndex = taskEvent.lastIndexOf(TO_DELIMITER);
                if (fromIndex < 0 || toIndex < 0) {
                    throw new IllegalArgumentException("Invalid Event Task, must have '/from' and '/to' flags");
                }
                String eventTask = taskEvent.substring(0, fromIndex - 1);
                String startDateString = taskEvent.substring(fromIndex + FROM_DELIMITER.length() + 1, toIndex - 1);
                String endDateString = taskEvent.substring(toIndex + TO_DELIMITER.length() +1);
                LocalDateTime startDate = parseDateTime(startDateString);
                LocalDateTime endDate = parseDateTime(endDateString);
                if (startDate == null || endDate == null) {
                    throw new IllegalArgumentException("Datetime must not be null.");
                }
                return new EventCommand(eventTask, startDate, endDate);
            case DELETE:
                String[] splitStringDelete = command.split(" ");
                if (splitStringDelete.length != 2) {
                    throw new IllegalArgumentException("Index is required.");
                }
                int index = Integer.parseInt(splitStringDelete[1]) - 1;
                return new DeleteCommand(index);
            case FIND:
                String[] splitStringFind = command.split(" ");
                String query = "";
                if (splitStringFind.length == 2) {
                    query =  splitStringFind[1];
                }
                return new FindCommand(query);
        }
        throw new IllegalArgumentException("Unknown action: " + command);
    }
}
