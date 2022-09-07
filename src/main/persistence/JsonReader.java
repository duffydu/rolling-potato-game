package persistence;

import model.LeadershipBoard;
import model.PlayerHistory;
import model.ScoreEntry;
import model.ScoreHistory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ScoreHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseScoreHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses scoreHistory from JSON object and returns it
    private ScoreHistory parseScoreHistory(JSONObject jsonObject) {
        String listType = jsonObject.getString("listType");
        ScoreHistory history;
        if (listType.equals("leadershipBoard")) {
            history = new LeadershipBoard();
        } else {
            history = new PlayerHistory();
        }
        history.setListType(listType);
        addScoreEntries(history, jsonObject);
        return history;
    }

    // MODIFIES: history
    // EFFECTS: parses scores entries from JSON object and adds them to history
    private void addScoreEntries(ScoreHistory history, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("scoreEntries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addScore(history, nextEntry);
        }
    }

    // MODIFIES: history
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addScore(ScoreHistory history, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        int score = jsonObject.getInt("score");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(jsonObject.getString("date"), formatter);
        ScoreEntry entry = new ScoreEntry(score, username, date);
        history.addScoreEntry(entry);
    }

}
