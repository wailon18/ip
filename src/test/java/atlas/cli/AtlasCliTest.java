package atlas.cli;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AtlasCliTest {

    @Test
    void readCommand_readsFromScanner() {
        String input = "todo read book\n";
        Scanner scanner = new Scanner(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        AtlasCli cli = new AtlasCli(scanner);

        String cmd = cli.readCommand();

        assertEquals("todo read book", cmd);
    }

    @Test
    void showError_printsMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        AtlasCli cli = new AtlasCli(new Scanner(InputStream.nullInputStream()));
        cli.showError("Invalid index.");

        assertTrue(out.toString().contains("Invalid index."));

        System.setOut(System.out);
    }
}
