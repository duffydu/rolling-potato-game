package persistence;

import model.ScoreEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkScoreEntry(String username, int score, String date, ScoreEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        assertEquals(username, entry.getUsername());
        assertEquals(score, entry.getScore());
        assertEquals(dateTime, entry.getDateTime());
    }
}
