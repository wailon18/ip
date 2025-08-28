package atlas.storage;

import atlas.tasklist.AtlasTaskList;
import atlas.tasks.Deadline;
import atlas.tasks.Event;
import atlas.tasks.Task;
import atlas.tasks.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AtlasStorageTest {

    @TempDir
    public Path tempDir;

    @Test
    void save_writesInExpectedFormat() throws IOException {
        Path file = tempDir.resolve("tasks.txt");
        AtlasStorage atlasStorage = new AtlasStorage(file.toString());

        AtlasTaskList taskList = new AtlasTaskList(new ArrayList<>());
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Deadline("submit ip",
                LocalDateTime.of(2025, 8, 29, 16, 0, 0)));
        taskList.addTask(new Event("project meeting",
                LocalDateTime.of(2025, 8, 29, 17, 0, 0),
                LocalDateTime.of(2025, 8, 29, 18, 0, 0)));
        taskList.getTaskByIndex(0).toggleComplete();

        atlasStorage.save(taskList);
        var lines = Files.readAllLines(file);
        assertEquals(3, lines.size());

        assertEquals("T|1|read book", lines.get(0));
        assertEquals("D|0|submit ip|29/08/2025 1600", lines.get(1));
        assertEquals("E|0|project meeting|29/08/2025 1700|29/08/2025 1800", lines.get(2));
    }

    @Test
    void load_loadsSavedTasks() throws IOException {
        Path file = tempDir.resolve("tasks.txt");
        Files.writeString(file,
                "T|1|read book\n" +
                "D|0|submit ip|29/8/2025 1600\n" +
                "E|0|project meeting|29/8/2025 1700|29/8/2025 1800\n");

        AtlasStorage atlasStorage = new AtlasStorage(file.toString());

        var tasks = atlasStorage.load();

        assertEquals(3, tasks.size());

        Task t0 = tasks.get(0);
        assertTrue(t0.isComplete());
        assertEquals("[T][X] read book", t0.toString());

        Task t1 = tasks.get(1);
        assertFalse(t1.isComplete());
        assertEquals("[D][ ] submit ip (by: 29 Aug 2025 1600)", t1.toString());

        Task t2 = tasks.get(2);
        assertFalse(t2.isComplete());
        assertEquals("[E][ ] project meeting (from: 29 Aug 2025 1700 to: 29 Aug 2025 1800)", t2.toString());

    }
}
