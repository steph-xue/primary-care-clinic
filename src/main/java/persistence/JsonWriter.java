package persistence;

import model.Clinic;

import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of clinic to file.
// JsonWriter code modeled from the JsonSerializationDemo.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs writer to write to destination file.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer, creating the destination's parent directories if needed.
    // Throws FileNotFoundException if destination file cannot be opened for writing.
    public void open() throws FileNotFoundException {
        File file = new File(destination);
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }
        writer = new PrintWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of clinic to file.
    public void write(Clinic clinic) {
        JSONObject json = clinic.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer.
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes string to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
