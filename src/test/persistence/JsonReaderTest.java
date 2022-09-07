package persistence;

import model.ScoreEntry;
import model.ScoreHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ScoreHistory sh = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyScoreHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyScoreHistory.json");
        try {
            ScoreHistory sh = reader.read();
            assertEquals("player", sh.getListType());
            assertEquals(0, sh.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayerHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayerHistory.json");
        try {
            ScoreHistory sh = reader.read();
            assertEquals("player", sh.getListType());
            List<ScoreEntry> history = sh.getScoreEntries();
            assertEquals(2, history.size());
            checkScoreEntry("duffy", 20, "2021-10-22 21:21", history.get(0));
            checkScoreEntry("duffy", 12, "2021-10-22 20:49", history.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLeadershipBoard() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLeadershipBoard.json");
        try {
            ScoreHistory sh = reader.read();
            assertEquals("leadershipBoard", sh.getListType());
            List<ScoreEntry> history = sh.getScoreEntries();
            assertEquals(2, history.size());
            checkScoreEntry("duffy", 20, "2021-10-22 21:21", history.get(0));
            checkScoreEntry("duffy", 12, "2021-10-22 20:49", history.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}