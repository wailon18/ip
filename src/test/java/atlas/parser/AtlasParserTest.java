package atlas.parser;

import atlas.commands.Actions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AtlasParserTest {

    @Test
    void parseDateTime_returnsDateTimeWithValidFormat() {
        LocalDateTime dateTime = AtlasParser.parseDateTime("28/08/2025 1000");
        assertNotNull(dateTime);
        assertEquals(2025, dateTime.getYear());
        assertEquals(8, dateTime.getMonthValue());
        assertEquals(28, dateTime.getDayOfMonth());
        assertEquals(10, dateTime.getHour());
    }

    @Test
    void parseDateTime_returnsNullWithInvalidFormat() {
        LocalDateTime dateTime = AtlasParser.parseDateTime("28-08-2025 1000");
        assertNull(dateTime);
    }

    @Test
    void toAction_returnsCorrectEnum() {
        assertEquals(Actions.LIST, AtlasParser.toAction("list"));
        assertEquals(Actions.LIST, AtlasParser.toAction("ls"));
        assertEquals(Actions.DELETE, AtlasParser.toAction("delete"));
        assertEquals(Actions.DELETE, AtlasParser.toAction("del"));
    }

    @Test
    void toAction_throwsErrorWithUnknownEnum() {
        assertThrows(IllegalArgumentException.class,
                () -> AtlasParser.toAction("foo"));
    }

    @Test
    void parse_throwsErrorWithMissingDateForDeadline() {
        assertThrows(IllegalArgumentException.class,
                () -> AtlasParser.parse("deadline submit ip"));
    }

    @Test
    void parse_throwsErrorWithMissingDatesForEvent() {
        assertThrows(IllegalArgumentException.class,
                () -> AtlasParser.parse("event project meeting"));
    }
}
