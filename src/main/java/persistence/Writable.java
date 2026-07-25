package persistence;

import org.json.JSONObject;

// Writable code modeled from the JsonSerializationDemo
// Writable interface to return JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
