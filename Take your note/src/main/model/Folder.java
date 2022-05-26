package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class Folder extends DefaultMutableTreeNode implements Writable {
    private List<Note> notes;
    private String folderName;

    // EFFECTS: build constructor
    public Folder(String folderName) {
        this.notes = new ArrayList<>();
        this.folderName = folderName;
    }

    //MODIFIES: this
    //EFFECTS: add note to this
    public void addNote(Note note) {
        notes.add(note);
        EventLog.getInstance().logEvent(new Event("Folder " + folderName
                + " has added note " + note.getTitle()));
    }

    //MODIFIES: this
    //EFFECTS: delete note from this
    public void deleteNote(Note note) {
        notes.remove(note);
    }

    //EFFECTS: get the size of notes
    public int getSize() {
        return notes.size();
    }

    //MODIFIES: this
    //EFFECTS: return note if exists, otherwise return null;
    public Note getNote(String title) {
        for (Note n: notes) {
            if (title.equals(n.getTitle())) {
                return n;
            }
        }

        return null;
    }

    //EFFECTS: get notes
    public List<Note> getNotes() {
        return notes;
    }

    //EFFECTS: get folder name
    public String getFolderName() {
        return folderName;
    }

    //EFFECTS: return true if note exists in this, otherwise return false
    public boolean noteExist(String title) {
        for (Note note: notes) {
            if (title.equals(note.getTitle())) {
                return true;
            }
        }

        return false;
    }

    //reference from JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("folderName", this.folderName);
        json.put("notes", notesToJson());
        return json;
    }

    //reference from JsonSerializationDemo
    // EFFECTS: returns notes in this.folder as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Note n : this.notes) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }
}
