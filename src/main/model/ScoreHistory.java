package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class ScoreHistory implements Writable {
    // Fields
    protected List<ScoreEntry> history;
    protected String listType;

    // Constructor
    // EFFECTS: initialize arraylist to store past score entry histories
    public ScoreHistory() {
        history = new ArrayList<>();
    }

    // Methods

    // MODIFIES: this
    // EFFECTS: Add a score entry to the score history
    //          Return true if entry was successfully added, false otherwise
    public abstract boolean addScoreEntry(ScoreEntry entry);

    public List<ScoreEntry> getScoreEntries() {
        return history;
    }

    // EFFECTS: search for one score entry on the leadership board by date and print
    //          returns true if such an entry exist in the history, false otherwise
    public boolean viewScoreEntry(LocalDateTime date) {
        for (ScoreEntry entry : history) {
            if (entry.getDateTime().equals(date)) {
                entry.printEntry();
                return true;
            }
        }
        return false;
    }

    // EFFECTS: search for one score entry on the leadership board by score and print
    //          returns true if such a score exist, false otherwise
    public boolean viewScoreEntry(int score) {
        for (ScoreEntry entry : history) {
            if (entry.getScore() == score) {
                entry.printEntry();
                return true;
            }
        }
        return false;
    }

    // EFFECTS: print the highest score entry
    //          returns the string representation of the entry if such an entry exist
    //          if the history is empty, return a string with appropriate message
    public abstract String viewHighestScoreEntry();

    public abstract ScoreEntry getHighestScoreEntry();

    // EFFECTS: print all the score records on the leadership board and return all the entries as an arraylist
    public abstract ArrayList<ScoreEntry> displayAllScores();

    // MODIFIES: this
    // EFFECT: keep the history of scores sorted according to scores in descending order
    protected void sortScores() {
        //sort list in descending order according to score (highest to lowest)
        history.sort(Comparator.comparing(ScoreEntry::getScore).reversed());
    }

    // EFFECTS: return the number of entries stored in history currently
    public int size() {
        return history.size();
    }


    @Override
    // EFFECTS: return a json object that has the information of the scoreHistory
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listType", listType);
        json.put("scoreEntries", scoresToJson());
        return json;
    }

    // EFFECTS: returns scores in player history as a JSON array
    public JSONArray scoresToJson() {
        JSONArray jsonArray = new JSONArray();
        for (ScoreEntry t : history) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String type) {
        listType = type;
    }

    public abstract String getUsername();

    public abstract void setUsername(String name);

}
