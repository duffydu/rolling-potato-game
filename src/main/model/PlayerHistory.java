package model;

//import java.time.LocalDateTime;
import java.util.*;

// Player is a class that creates a player and stores that player's username and past score histories
public class PlayerHistory extends ScoreHistory {
    // Fields
    private String username;

    // Constructors

    // EFFECTS: create an empty arraylist to store future scores related to this player
    //          set username to "Guest"
    public PlayerHistory() {
        super();
        username = "Guest";
        super.listType = "player";
    }

    // EFFECTS: create an empty arraylist to store future scores related to this player
    //          set username to the parameter playerUsername
    public PlayerHistory(String playerUsername) {
        super();
        username = playerUsername;
        super.listType = "player";
    }

    // Methods

    // MODIFIES: this
    // EFFECTS: Add a score entry to the score history
    //          Return true if entry was successfully added, false otherwise
    public boolean addScoreEntry(ScoreEntry entry) {
        super.history.add(entry);
        //sort list in descending order according to score (highest to lowest)
        super.sortScores();

        // event logging
        Event event = new Event("New score entry " + entry.printEntry() + " is added to Player History");
        EventLog.getInstance().logEvent(event);

        return true;
    }


    // EFFECTS: print the highest score entry by this player
    //          returns true if such a score exist, false otherwise (empty record)
    public String viewHighestScoreEntry() {
        String text;
        if (super.history.isEmpty()) {
            text = "No score history available for player " + username;
        } else {
            text = super.history.get(0).printEntry();
        }
        return text;
    }

    //Effects: return the highest ScoreEntry in the playerHistory
    @Override
    public ScoreEntry getHighestScoreEntry() {
        return super.history.get(0);
    }

    // EFFECTS: print and return all the score records of this player
    public ArrayList<ScoreEntry> displayAllScores() {
        ArrayList<ScoreEntry> listOfScoreEntries = new ArrayList<>();
        System.out.println("Player " + username + "'s score history: ");
        for (ScoreEntry entry : super.history) {
            entry.printEntry();
            listOfScoreEntries.add(entry);
        }
        return listOfScoreEntries;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }
}
