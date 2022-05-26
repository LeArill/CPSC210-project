package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Note extends DefaultMutableTreeNode implements Writable {
    private String title;
    private String content;

    // EFFECTS: build constructor
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //EFFECTS: get note title
    public String getTitle() {
        return this.title;
    }

    //EFFECTS: get note content
    public String getContent() {
        return this.content;
    }

    //EFFECTS: set title
    public void setTitle(String title) {
        this.title = title;
    }

    //EFFECTS: set content
    public void setContent(String content) {
        this.content = content;
    }

    //reference from JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("content", this.content);

        return json;
    }


}
