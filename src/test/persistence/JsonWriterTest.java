package persistence;

import model.PlayerHistory;
import model.ScoreEntry;
import model.ScoreHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ScoreHistory sh = new PlayerHistory("duffy");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyScoreHistory() {
        try {
            ScoreHistory sh = new PlayerHistory("duffy");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyScoreHistory.json");
            writer.open();
            writer.write(sh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyScoreHistory.json");
            sh = reader.read();
            assertEquals("player", sh.getListType());
            assertEquals(0, sh.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayerHistory() {
        try {
            ScoreHistory sh = new PlayerHistory("duffy");
            ScoreEntry s1 = new ScoreEntry(20, "duffy");
            ScoreEntry s2 = new ScoreEntry(12, "duffy");
            sh.addScoreEntry(s1);
            sh.addScoreEntry(s2);
            s1.setDateAndTime(2021, 10, 22, 21, 21, 00);
            s2.setDateAndTime(2021, 10, 22, 20, 49, 00);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralScoreHistory.json");
            writer.open();
            writer.write(sh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralScoreHistory.json");
            sh = reader.read();
            assertEquals("player", sh.getListType());
            List<ScoreEntry> history = sh.getScoreEntries();
            assertEquals(2, history.size());
            checkScoreEntry("duffy", 20, "2021-10-22 21:21", history.get(0));
            checkScoreEntry("duffy", 12, "2021-10-22 20:49", history.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}