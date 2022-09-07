package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

//Tests for methods in package model.ScoreEntry
public class ScoreEntryTest {
    private ScoreEntry emptyEntry;
    private ScoreEntry entry1;

    @BeforeEach
    public void runBefore(){
        emptyEntry = new ScoreEntry();
        entry1= new ScoreEntry(13, "duffpuff");
    }

    @Test
    public void testIsEmpty(){
        assertTrue(emptyEntry.isEmpty());
        assertFalse(entry1.isEmpty());
        emptyEntry.setScore(10);
        assertFalse(emptyEntry.isEmpty());
        emptyEntry.setScore(0);
        emptyEntry.setUsername("Jess");
        assertFalse(emptyEntry.isEmpty());
    }

    @Test
    public void testPrintEntry(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        assertEquals(
                entry1.getUsername() + " got a score of " + entry1.getScore()  + " on " + entry1.getDateTime().format(formatter),
                entry1.printEntry()
        );
        emptyEntry.setScore(10);
        emptyEntry.setUsername("Jess");
        assertEquals(
                emptyEntry.getUsername() + " got a score of " + emptyEntry.getScore()  + " on " + emptyEntry.getDateTime().format(formatter),
                emptyEntry.printEntry()
        );
    }

    @Test
    public void testSetDateAndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        entry1.setDateAndTime(2020, 10,29, 20,22,00);

        assertEquals("2020-10-29 20:22", entry1.getDateTime().format(formatter));
    }

    @Test
    public void testToJson(){
        JSONObject json = new JSONObject();
        json.put("username", "duffpuff");
        json.put("score", 13);
        json.put("date", "2020-10-29 20:22");
        entry1.setDateAndTime(2020, 10, 29, 20, 22, 00);
        JSONAssert.assertEquals(json, entry1.toJson(), false);
    }
}
