package atlas.storage;

import atlas.parser.AtlasParser;
import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Deadline;
import atlas.tasks.Event;
import atlas.tasks.Task;
import atlas.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles saving and loading tasks to and from a file.
 * Provides methods to persist the task list and reconstruct it from storage.
 */
public class AtlasStorage {
    private String filePath;

    public AtlasStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves all tasks in the given task list to the file.
     * Overwrites the file with the current state of the task list.
     *
     * @param atlasTaskList the task list to save
     * @throws IOException if an error occurs while writing to the file
     */
    public void save(AtlasTaskList atlasTaskList) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(this.filePath, false))) {
            for (Task task : atlasTaskList.getTaskList()) {
                out.println(task.saveTask());
            }
        }
    }

    /**
     * Loads tasks from the file and reconstructs them as a list.
     * Supports Todo, Deadline, and Event tasks, restoring their completion state.
     *
     * @return a list of tasks loaded from the file; empty if the file does not exist
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitString = line.split("\\|");
                String type = splitString[0];
                Task newTask;
                if (type.equals("T")) {
                    newTask = new Todo(splitString[2]);
                } else if (type.equals("D")) {
                    LocalDateTime deadline = AtlasParser.parseDateTime(splitString[3]);
                    if (deadline == null) {
                        continue;
                    }
                    newTask = new Deadline(splitString[2], deadline);
                } else if (type.equals("E")) {
                    LocalDateTime startDate = AtlasParser.parseDateTime(splitString[3]);
                    LocalDateTime endDate = AtlasParser.parseDateTime(splitString[4]);
                    if (startDate == null) {
                        continue;
                    }
                    newTask = new Event(splitString[2], startDate, endDate);
                } else {
                    throw new IllegalArgumentException("Invalid task type: " + type);
                }
                if (splitString[1].equals("1")) {
                    newTask.toggleComplete();
                }
                tasks.add(newTask);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            System.out.println("Error loading tasks: " + ex.getMessage());
        }
        return tasks;
    }
}
