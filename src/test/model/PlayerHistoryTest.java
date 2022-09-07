package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Tests for methods in package model.Player
public class PlayerHistoryTest {
    private PlayerHistory playerHistory1;
    private PlayerHistory playerHistory2;
    private ScoreEntry score1;
    private ScoreEntry score2;

    @BeforeEach
    public void runBefore(){
        playerHistory1 = new PlayerHistory();
        playerHistory2 = new PlayerHistory("duffpuff");
        score1 = new ScoreEntry(10, playerHistory1.getUsername());
        score2 = new ScoreEntry(13, playerHistory2.getUsername());
    }

    @Test
    public void testAddScoreEntry(){
        playerHistory1.addScoreEntry(score1);
        playerHistory2.addScoreEntry(score2);
        assertTrue(playerHistory1.viewScoreEntry(10));
        assertFalse(playerHistory1.viewScoreEntry(13));
        assertTrue(playerHistory2.viewScoreEntry(13));
        assertFalse(playerHistory2.viewScoreEntry(10));
    }

    @Test
    public void testViewHighestScoreEntry(){
        score2.setUsername("duffpuff");
        playerHistory2.addScoreEntry(score1);
        playerHistory2.addScoreEntry(score2);

        //assertTrue(playerHistory2.viewHighestScoreEntry());
        assertEquals(13, playerHistory2.displayAllScores().get(0).getScore());
        assertEquals(10, playerHistory2.displayAllScores().get(1).getScore());
        //assertFalse(playerHistory1.viewHighestScoreEntry());
    }

    @Test
    public void testViewHighestScoreEntryEmpty(){
        assertEquals("No score history available for player " + playerHistory2.getUsername(), playerHistory2.viewHighestScoreEntry());
    }


    @Test
    public void testViewHighestScoreEntryNonEmpty(){
        playerHistory2.addScoreEntry(score1);
        playerHistory2.addScoreEntry(score2);
        assertEquals(playerHistory2.getHighestScoreEntry().printEntry(), playerHistory2.viewHighestScoreEntry());
    }

//    @Test
//    public void testDisplayAllScores(){
//        score2.setUsername("duffpuff");
//        player2.addScoreEntry(score1);
//        player2.addScoreEntry(score2);
//
//        assertTrue(player2.viewHighestScoreEntry());
//        assertFalse(player1.viewHighestScoreEntry());
//    }

    @Test
    public void testDisplayLeadershipBoard() {
        fillBoard(0);
        ArrayList<ScoreEntry> list = playerHistory2.displayAllScores();
        for (int i = 0; i < playerHistory2.size(); i++){
            assertEquals(list.get(i).getScore(), playerHistory2.size()-1-i);
        }
    }

    @Test
    public void testToJson(){
        playerHistory2.addScoreEntry(score1);
        playerHistory2.addScoreEntry(score2);
        JSONObject json = new JSONObject();
        json.put("listType", playerHistory2.getListType());
        json.put("scoreEntries", playerHistory2.scoresToJson());
        JSONAssert.assertEquals(json, playerHistory2.toJson(), false);
    }


    @Test
    public void testGetListType(){
        assertEquals("player", playerHistory2.getListType());
    }

    @Test
    public void testSetListType(){
        playerHistory2.setListType("leadershipBoard");
        assertEquals("leadershipBoard", playerHistory2.getListType());
        playerHistory2.setListType("player");
        assertEquals("player", playerHistory2.getListType());
    }

    @Test
    public void testGetScoreEntries(){
        playerHistory2.addScoreEntry(score2);
        playerHistory2.addScoreEntry(score1);
        assertEquals(score2.getScore(), playerHistory2.getScoreEntries().get(0).getScore());
        assertEquals(score2.getUsername(), playerHistory2.getScoreEntries().get(0).getUsername());
        assertEquals(score1.getScore(), playerHistory2.getScoreEntries().get(1).getScore());
        assertEquals(score1.getUsername(), playerHistory2.getScoreEntries().get(1).getUsername());
    }

    @Test
    public void testSetUsername(){
        playerHistory2.setUsername("jessie");
        assertEquals("jessie", playerHistory2.getUsername());
    }

    public void fillBoard(int n){
        for (int i = n; i < LeadershipBoard.MAX_ENTRIES + n; i++) {
            playerHistory2.addScoreEntry(new ScoreEntry( i, "duffpuff"));
        }
    }
}
