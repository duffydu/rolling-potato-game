package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// ScoreEntry is a class that represents one entry of score on either the leadership board or in player's records
// Each ScoreEntry object has that entry's:
//                                          score,
//                                          date and time at which the score was recorded,
//                                          and the username associated with that score
public class ScoreEntry implements Writable {
    // Fields
    int score;
    LocalDateTime dateTime;
    String username;

    // Constructor
    // EFFECTS: initialize score to 0, username to empty string, and dateTime to current date and time
    public ScoreEntry() {
        //initialize variables
        score = 0;
        username = "";
        dateTime  = LocalDateTime.now();
    }

    // EFFECTS: initialize score, username according to parameters, and dateTime to current date and time
    public ScoreEntry(int playerScore, String playerUsername) {
        //initialize variables
        score = playerScore;
        username = playerUsername;
        dateTime  = LocalDateTime.now();
    }

    // EFFECTS: initialize score, username according to parameters, and dateTime to current date and time
    public ScoreEntry(int playerScore, String playerUsername, LocalDateTime date) {
        //initialize variables
        score = playerScore;
        username = playerUsername;
        dateTime  = date;
    }
    // Methods

    // EFFECTS: returns true if neither username nor score of scoreEntry have not been set to a meaningful value
    //          otherwise return false
    public boolean isEmpty() {
        return (username.equals("") && score == 0);
    }

    public int getScore() {
        return score;
    }

    // EFFECTS: printing the fields of the ScoreEntry in a meaningful way and return the printed string
    public String printEntry() {
        String output = username + " got a score of " + score + " on " + formatDateToString(dateTime);
        System.out.println(output);
        return output;
    }

    // MODIFIES: this
    // EFFECTS: set the score to param passed in
    public void setScore(int scoreRecord) {
        score = scoreRecord;
    }

    // EFFECTS: return the date and time object
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // MODIFIES: this
    // EFFECTS: set the date and time according to params passed in
    public void setDateAndTime(int year, int month, int day, int hour, int minute, int second) {
        dateTime = dateTime.of(year, month, day, hour, minute, second);
    }

    // EFFECTS: return the username
    public String getUsername() {
        return username;
    }

    // MODIFIES: this
    // EFFECTS: set the username to param passed in
    public void setUsername(String playerUsername) {
        username = playerUsername;
    }

    @Override
    // EFFECTS: return a json object that has the information of the scoreEntry
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("score", score);
        json.put("date", formatDateToString(dateTime));
        return json;
    }

    // EFFECTS: helper method that takes a LocalDateTime object and return the string representation of it in the
    //          format of yyyy-MM-dd HH:mm
    public String formatDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter); // "1986-04-08 12:30"
    }

}
