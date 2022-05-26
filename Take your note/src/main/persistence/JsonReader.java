package persistence;

import model.Folder;
import model.Note;
import model.Sync;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Sync read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Sync parseWorkRoom(JSONObject jsonObject) {
        Sync sync = new Sync();

        addFolders(sync, jsonObject);
        return sync;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addFolders(Sync sync, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("hierarchy");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addFolder(sync, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addFolder(Sync sync, JSONObject jsonObject) {
        String folderName = jsonObject.getString("folderName");
        Folder folder;

        if (folderName.equals("demobook")) {
            folder = sync.getDemobook();
            addNotes(folder, jsonObject);
        } else if (folderName.equals("trash")) {
            folder = sync.getTrash();
            addNotes(folder, jsonObject);
        } else {
            folder = new Folder(folderName);
            addNotes(folder, jsonObject);
            sync.addFolder(folder);
        }
    }

    private void addNotes(Folder folder, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addNote(folder, nextThingy);
        }
    }

    private void addNote(Folder folder, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");

        Note note = new Note(title, content);

        folder.addNote(note);
    }
}
