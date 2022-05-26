package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class Sync implements Writable {
    private List<Folder> hierarchy;
    private Folder demobook;
    private Folder trash;

    //EFFECTS: build constructor
    public Sync() {
        this.hierarchy = new ArrayList<>();

        demobook = new Folder("demobook");
        trash = new Folder("trash");

        hierarchy.add(demobook);
        hierarchy.add(trash);
    }

    //MODIFIES: this
    //EFFECTS: add folder to this
    public void addFolder(Folder folder) {
        this.hierarchy.add(folder);
    }

    //MODIFIES: this
    //EFFECTS: get folder
    public Folder getFolder(String folderName) {
        for (Folder f: this.hierarchy) {
            if (folderName.equals(f.getFolderName())) {
                return f;
            }
        }

        return null;
    }

    //EFFECTS: get size of this.hierarchy
    public int getSize() {
        return this.hierarchy.size();
    }

    //EFFECTS: get demobook
    public Folder getDemobook() {
        return demobook;
    }

    //EFFECTS: get trash
    public Folder getTrash() {
        return trash;
    }

    //EFFECTS: get hierarchy
    public List<Folder> getHierarchy() {
        return this.hierarchy;
    }

    //MODIFIES: this
    //EFFECTS: return true if folder exist in this.hierarchy
    public boolean folderExist(String folderName) {
        for (Folder f : hierarchy) {
            if (folderName.equals(f.getFolderName())) {
                return true;
            }
        }
        return false;
    }

    //reference from JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hierarchy", foldersToJson());
        return json;
    }

    //reference from JsonSerializationDemo
    // EFFECTS: returns folders in this.hierarchy as a JSON array
    private JSONArray foldersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Folder f: this.hierarchy) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
