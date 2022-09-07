package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
    LeadershipBoard is a class that represents a leadership board that stores the top 10 score entries
    in descending order according to the score
*/
public class LeadershipBoard extends ScoreHistory {
    // Constants
    public static final int MAX_ENTRIES = 5;

    // Event Logging Fields
    Event event;

    // Constructor
    // EFFECTS: initialize arraylist to store score entries
    public LeadershipBoard() {
        super();
        super.listType = "leadershipBoard";
    }

    // Methods

    // MODIFIES: this
    // EFFECTS: Add a score entry to the leadership board if the leadership board is not full
    //          If it is full, compare the entry with the last item on the board
    //          If it has a higher score than the last entry, then remove the last entry on the board and add entry
    //          If it is not higher than the last entry, print score is too low to be added to the leadership board
    //          After adding, sort all entries in descending order according to score
    //          Return true if entry was successfully added, false otherwise
    public boolean addScoreEntry(ScoreEntry entry) {
        if (!isFull()) {
            super.history.add(entry);
            sortScores();
            // event logging
            event = new Event("New score entry " + entry.printEntry()  + " is added to Leadership Board");
            EventLog.getInstance().logEvent(event);
            return true;
        } else if (entry.getScore() > super.history.get(super.history.size() - 1).getScore()) {
            // event logging (removal)
            event = new Event("Score entry "
                    + super.history.get(super.history.size() - 1).printEntry()
                    + " is removed from Leadership Board");
            EventLog.getInstance().logEvent(event);
            super.history.remove(super.history.size() - 1);
            super.history.add(entry);
            sortScores();

            // event logging (addition)
            event = new Event("New score entry " + entry.printEntry() + " is added to Leadership Board");
            EventLog.getInstance().logEvent(event);

            return true;
        }
        System.out.println("score is too low to be added to the leadership board");
        return false;
    }

    // EFFECTS: print the highest score entry on the leadership board and
    //          returns true if such an entry exist on the leadership board
    //          if the leadership board is empty, print appropriate message in console and return false
    public String viewHighestScoreEntry() {
        String text;
        if (super.history.isEmpty()) {
            text = "No score was added to the leadership board";
        } else {
            text = super.history.get(0).printEntry();
        }
        return text;
    }

    // EFFECTS: return the ScoreEntry that has the highest sore in history
    public ScoreEntry getHighestScoreEntry() {
        return super.history.get(0);
    }

    // EFFECTS: print all the score records on the leadership board and return all the entries as an arraylist
    public ArrayList<ScoreEntry> displayAllScores() {
        ArrayList<ScoreEntry> list = new ArrayList<>();
        System.out.println("Leadership Board as of " + LocalDateTime.now() + ": ");
        for (ScoreEntry entry : super.history) {
            entry.printEntry();
            list.add(entry);
        }
        return list;
    }

    // EFFECTS: returns true if the leadership board is full (max 10 entries), false otherwise
    public boolean isFull() {
        return super.history.size() == MAX_ENTRIES;
    }

    //EFFECTS: return an empty string of username
    public String getUsername() {
        return "";
    }

    public void setUsername(String name) {
    }

}
