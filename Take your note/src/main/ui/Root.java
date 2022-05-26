package ui;

import model.Folder;
import model.Note;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class Root extends DefaultMutableTreeNode {

    //EFFECTS: init the DefaultTreeNode
    public Root(List<Folder> folderList) {
        super("Take Your Note App");

        createNodes(folderList);
    }

    //MODIFIES: this
    //EFFECTS: create new DefaultTreeNode
    public void createNodes(List<Folder> folders) {
        for (Folder folderNode: folders) {
            String folderName = folderNode.getFolderName();
            folderNode.setUserObject(folderName);
            for (Note noteNode: folderNode.getNotes()) {
                String noteTitle = noteNode.getTitle();
                noteNode.setUserObject(noteTitle);
                folderNode.add(noteNode);
            }

            add(folderNode);
        }
    }
}
