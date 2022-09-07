package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/*
    ScoreManager is a class that manages and updates the scores in playerHistory, LeadershipBoard
     and reading from and writing to files
*/
public class ScoreManager {
    // file paths
    private static final String JSON_STORE_PLAYER = "./data/playerscores.json";
    private static final String JSON_STORE_LEADERBOARD = "./data/leaderboardscores.json";

    // Fields
    private ScoreHistory leadershipBoard;
    private ScoreHistory playerHistory;
    private int scoreCount;
    private int highScore;
    private ScoreEntry entry;

    // file IO fields
    private JsonWriter playerJsonWriter;
    private JsonWriter leaderBoardJsonWriter;
    private JsonReader playerJsonReader;
    private JsonReader leaderBoardJsonReader;


    // constructor
    public ScoreManager() {
        leadershipBoard = new LeadershipBoard();
        scoreCount = 0;
        highScore = 0;
        initializeFileIO();
    }

    // MODIFIES: this
    // EFFECTS: set the scoreCount to the value passed in
    public void setScoreCount(int score) {
        scoreCount = score;
    }

    // EFFECTS: return the scoreCount variable
    public int getScoreCount() {
        return scoreCount;
    }

    // MODIFIES: this
    // EFFECT: set the highScore variable to the first entry on the leadershipBoard
    public void updateHighScore() {
        highScore = leadershipBoard.getHighestScoreEntry().getScore();
        System.out.println("High score set to " + highScore);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setPlayerHistory(ScoreHistory history) {
        playerHistory = history;
    }

    public ScoreHistory getPlayerHistory() {
        return playerHistory;
    }

    public ScoreHistory getLeadershipBoard() {
        return leadershipBoard;
    }

    // MODIFIES: this
    // EFFECTS: construct a ScoreEntry object with the specified score and player's username
    //          then add the ScoreEntry to the playerHistory and leadershipBoard
    public void addScoreEntry(int score) {
        scoreCount = score;
        entry = new ScoreEntry();
        entry.setScore(score);
        entry.setUsername(playerHistory.getUsername());
        playerHistory.addScoreEntry(entry);
        leadershipBoard.addScoreEntry(entry);
        entry.printEntry();
    }

    // MODIFIES: this
    // EFFECTS: instantiate the file readers and writers used for file IO
    private void initializeFileIO() {
        playerJsonWriter = new JsonWriter(JSON_STORE_PLAYER);
        leaderBoardJsonWriter = new JsonWriter(JSON_STORE_LEADERBOARD);
        playerJsonReader = new JsonReader(JSON_STORE_PLAYER);
        leaderBoardJsonReader = new JsonReader(JSON_STORE_LEADERBOARD);
    }

    // MODIFIES: corresponding files
    // EFFECTS: saves the scores in the current game session to file
    public void saveScores() {
        try {
            playerJsonWriter.open();
            playerJsonWriter.write(playerHistory);
            playerJsonWriter.close();
            System.out.println("Saved " + entry.getUsername() + " to " + JSON_STORE_PLAYER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_PLAYER);
        }

        try {
            leaderBoardJsonWriter.open();
            leaderBoardJsonWriter.write(leadershipBoard);
            leaderBoardJsonWriter.close();
            System.out.println("Saved " + entry.getUsername() + " to " + JSON_STORE_LEADERBOARD);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_LEADERBOARD);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads score history from file and save information into playerHistory
    public boolean loadScoreHistory() {
        try {
            ScoreHistory temp = playerJsonReader.read();
            if (playerHistory != null) {
                for (ScoreEntry entry : temp.getScoreEntries()) {
                    playerHistory.addScoreEntry(entry);
                }
            } else {
                playerHistory = temp;
            }
            System.out.println("Loaded " + playerHistory.getListType() + " from " + JSON_STORE_PLAYER);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_PLAYER);
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: loads leadership board scores from file and save information into leadershipBoard
    public void loadLeadershipBoard() {
        try {
            leadershipBoard = leaderBoardJsonReader.read();
            System.out.println("Loaded " + leadershipBoard.getListType() + " from " + JSON_STORE_LEADERBOARD);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_LEADERBOARD);
        }
    }

    // EFFECTS: prints a summary of event log in the console after exiting the game
    public void printEventLog() {
        //print event log in console
        System.out.println("Event Log: ");
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }
}
