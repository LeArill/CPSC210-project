package ui;

import model.Folder;
import model.Note;
import model.Sync;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MyTree extends JTree implements TreeSelectionListener {
    private Sync sync;
    private DefaultTreeModel treeModel;

    private MyTextField myTextField;
    private MyTextArea myTextArea;

    private Root root;

    private DefaultMutableTreeNode selectedNode;

    private static boolean DEBUG = false;

    //EFFECTS: init JTree
    public MyTree(Sync sync, MyTextField myTextField, MyTextArea myTextArea) {
        super();

        this.sync = sync;
        this.myTextField = myTextField;
        this.myTextArea = myTextArea;


        List<Folder> folderList;
        folderList = this.sync.getHierarchy();
        this.root = new Root(folderList);
        treeModel = new DefaultTreeModel(root);

        setModel(treeModel);

        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        setEditable(true);
        setShowsRootHandles(true);

        //Listen for when the selection changes.
        addTreeSelectionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: set new sync to this
    public void readSync(Sync sync) {
        this.sync = sync;
    }

    //MODIFIES: this
    //EFFECTS: get this.root
    public DefaultMutableTreeNode getRoot() {
        return this.root;
    }

    //MODIFIES: this
    //EFFECTS: set new root to this
    public void updateTree() {
        List<Folder> folderList = this.sync.getHierarchy();
        this.root = new Root(folderList);

        treeModel.setRoot(this.root);
    }

    //MODIFES: this
    //EFFECTS: listen to tree selection event
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            if (node instanceof Note) {
                myTextField.setText(((Note) node).getTitle());
                myTextArea.setText(((Note) node).getContent());
            }

            if (DEBUG) {
                System.out.print("I don't know what to say");
            }
        } else {
            System.out.println("Still don't know what to say");
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }

}
