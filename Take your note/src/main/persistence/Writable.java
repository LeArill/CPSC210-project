package persistence;

import org.json.JSONObject;

//reference from JasonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
