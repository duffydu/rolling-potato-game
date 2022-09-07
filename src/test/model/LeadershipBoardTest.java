package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Tests for methods in package model.LeadershipBoard
public class LeadershipBoardTest {
    LeadershipBoard board;
    ScoreEntry entry1;

    @BeforeEach
    public void runBefore(){
        board = new LeadershipBoard();
        entry1 = new ScoreEntry(13, "duffpuff");
    }

    @Test
    public void testAddScoreEntryWithEmptyBoard() {
        //assertFalse(board.viewHighestScoreEntry());
        assertTrue(board.addScoreEntry(entry1));
        assertTrue(board.viewScoreEntry(13));
        assertTrue(board.viewScoreEntry(entry1.getDateTime()));
        //assertTrue(board.viewHighestScoreEntry());
    }

    @Test
    public void testAddScoreEntryWithFullBoardSuccessful() {
        fillBoardTillFull(0);
        assertTrue(board.addScoreEntry(entry1));
        assertTrue(board.viewScoreEntry(13));
        assertTrue(board.viewScoreEntry(entry1.getDateTime()));
    }

    @Test
    public void testAddScoreEntryWithFullBoardUnsuccessful() {
        fillBoardTillFull(1);
        entry1.setScore(0);
        assertFalse(board.addScoreEntry(entry1));
        assertFalse(board.viewScoreEntry(0));
        entry1.setScore(1);
        assertFalse(board.addScoreEntry(entry1));
         //even though this score entry was not added,
        // but an older score entry with the same score still exist in the leadership board
        assertTrue(board.viewScoreEntry(1));
    }

    @Test
    public void testViewScoreEntryByDate() {
        board.addScoreEntry(entry1);
        assertTrue(board.viewScoreEntry(entry1.getDateTime()));
        assertFalse(board.viewScoreEntry(LocalDateTime.now().minusHours(3)));
    }

    @Test
    public void testViewHighestScoreEntryEmpty(){
        assertEquals("No score was added to the leadership board", board.viewHighestScoreEntry());
    }


    @Test
    public void testViewHighestScoreEntryNonEmpty(){
        board.addScoreEntry(entry1);
        assertEquals(board.getHighestScoreEntry().printEntry(), board.viewHighestScoreEntry());
    }

    @Test
    public void testDisplayLeadershipBoard() {
        fillBoardTillFull(0);
        ArrayList<ScoreEntry> list = board.displayAllScores();
        for (int i = 0; i < board.size(); i++){
            assertEquals(list.get(i).getScore(), board.size()-1-i);
        }
    }

    @Test
    public void testGetUsername() {
        board.addScoreEntry(entry1);
        assertEquals("", board.getUsername());
    }

    @Test
    public void testIsFull() {
        assertFalse(board.isFull());
        fillBoardTillFull(0);
        assertTrue(board.isFull());
    }

    public void fillBoardTillFull(int n){
        for (int i = n; i < LeadershipBoard.MAX_ENTRIES + n; i++) {
            board.addScoreEntry(new ScoreEntry( i, "duffpuff"));
        }
    }

}
