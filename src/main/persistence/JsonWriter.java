package persistence;

import model.ScoreEntry;
import model.ScoreHistory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of workroom to file
// adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String path;

    // Constructor
    // EFFECTS: constructs writer to write to specified path of the file
    public JsonWriter(String path) {
        this.path = path;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    //          throws FileNotFoundException if file cannot be found in the path
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ScoreEntry to file
    public void write(ScoreHistory sh) {
        JSONObject json = sh.toJson();
        saveToFile(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
