package atlas.parser;

import atlas.commands.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides parsing utilities for the Atlas application.
 * Converts raw user input into executable commands,
 * maps text tokens to actions, and parses date-time strings.
 */
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

    /**
     * Parses a string into a {@link LocalDateTime} using the format d/M/yyyy HHmm.
     * Returns {@code null} if the input is invalid.
     *
     * @param date the date-time string to parse
     * @return the parsed {@link LocalDateTime}, or {@code null} if parsing fails
     */
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

    /**
     * Converts a command token into an {@link Actions} enum value.
     * Accepts command names and aliases (case-insensitive).
     *
     * @param token the raw command string entered by the user
     * @return the corresponding {@link Actions} value
     * @throws IllegalArgumentException if the token does not match any action
     */
    public static Actions toAction(String token) {
        String key = token.trim().toUpperCase();
        Actions a = ACTION_MAP.get(key);
        if (a == null) {
            throw new IllegalArgumentException("Unknown action: " + token);
        }
        return a;
    }

    /**
     * Parses a full user command string into a corresponding {@link Command}.
     * <p>
     * Determines the action keyword, validates arguments, and constructs
     * the appropriate command object (e.g. {@code TodoCommand}, {@code DeadlineCommand}).
     *
     * @param command the raw user input string
     * @return the constructed {@link Command} matching the user input
     * @throws IllegalArgumentException if the command is invalid or missing arguments
     * @throws IndexOutOfBoundsException if an index argument is out of range
     */
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
            }
            int indexUnmark = Integer.parseInt(splitStringUnmark[1]) - 1;
            return new UncompleteCommand(indexUnmark);
        case MARK:
            String[] splitStringMark = command.split(" ");
            if (splitStringMark.length != 2) {
                throw new IllegalArgumentException("Index is required.");
            }
            int indexMark = Integer.parseInt(splitStringMark[1]) - 1;
            return new CompleteCommand(indexMark);
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
            String endDateString = taskEvent.substring(toIndex + TO_DELIMITER.length() + 1);
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
