package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Player;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {
    ScoreManager manager;
    int score;
    int highScore;
    ScoreEntry entry1;
    ScoreEntry entry2;

    @BeforeEach
    public void runBefore(){
        manager = new ScoreManager();
        entry1 = new ScoreEntry(13, "duffpuff");
        entry2 = new ScoreEntry(115, "duffpuff");
        score = 0;
        highScore = 0;
    }

    @Test
    public void testLoadScoreHistorySuccessful() {
        assertTrue(manager.loadScoreHistory());
        assertNotNull(manager.getPlayerHistory());
    }

    @Test
    public void testLoadLeadershipBoardSuccessful() {
        manager.loadLeadershipBoard();
        assertNotNull(manager.getLeadershipBoard());
    }

    @Test
    public void testSetScoreCount() {
        manager.setScoreCount(150);
        assertEquals(150, manager.getScoreCount());
    }

    @Test
    public void testUpdateHighScore() {
        manager.loadLeadershipBoard();
        highScore = manager.getLeadershipBoard().getHighestScoreEntry().getScore();
        manager.updateHighScore();
        assertEquals(highScore, manager.getHighScore());
    }

    @Test
    public void testSetPlayerHistory() {
        ScoreHistory p1 = new PlayerHistory();
        p1.addScoreEntry(entry1);
        p1.addScoreEntry(entry2);
        manager.setPlayerHistory(p1);
        assertEquals(p1, manager.getPlayerHistory());
    }

    @Test
    public void testAddScoreEntry() {
        manager.loadScoreHistory();
        ScoreHistory playerHistory = manager.getPlayerHistory();
        ScoreHistory leaderBoard = manager.getPlayerHistory();
        manager.addScoreEntry(2000000);

        assertEquals(2000000, playerHistory.getScoreEntries().get(0).getScore());
        assertEquals(2000000, leaderBoard.getScoreEntries().get(0).getScore());
    }

}
