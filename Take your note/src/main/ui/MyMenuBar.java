package ui;

import exceptions.FolderExist;
import exceptions.FolderNotFind;
import exceptions.NoteNotFind;
import model.Folder;
import model.Management;
import model.Note;
import model.Sync;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

public class MyMenuBar extends JMenuBar implements ActionListener {

    private static final String JSON_STORE = "./data/takeyournoteapp.json";

    private JFrame frame;
    private MyTree tree;
    private MyTextField myTextField;
    private MyTextArea myTextArea;

    private JMenu file;
    private JMenu edit;
    private JMenu help;

    // Define submenue for file
    private JMenuItem addFolder;
    private JMenuItem addNote;
    private JMenuItem delete;
    private JMenuItem recover;
    private JMenuItem save;

    private JMenuItem saveFile;
    private JMenuItem loadFile;

    private JMenuItem tryYourBest;

    private Management management;
    private Sync sync;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this
    //EFFECTS: init JMenu and its componenets
    public MyMenuBar(Management management, JFrame frame, MyTree tree,
                     MyTextField myTextField, MyTextArea myTextArea) {
        super();

        this.management = management;
        this.frame = frame;
        this.tree = tree;
        this.myTextField = myTextField;
        this.myTextArea = myTextArea;

        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");

        saveFile = new JMenuItem("Save file");
        loadFile = new JMenuItem("Load file");

        addFolder = new JMenuItem("Add new notebook");
        addNote = new JMenuItem("Add new note");
        delete = new JMenuItem("Delete your note");
        recover = new JMenuItem("Recover your note");
        save = new JMenuItem("Save change");

        tryYourBest = new JMenuItem("Try your best:)");

        initJson();

        buildMenuBar();
        perfromAction();
    }

    //MODIFIES: this
    //EFFECTS: init Json
    private void initJson() {
        sync = management.getSync();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: assemble the components
    private void buildMenuBar() {
        file.add(saveFile);
        file.add(loadFile);

        edit.add(addFolder);
        edit.add(addNote);
        edit.add(delete);
        edit.add(recover);
        edit.add(save);
        edit.add(tryYourBest);

        add(file);
        add(edit);
        add(help);
    }

    //MODIFIES: this
    //EFFECTS: add ActionListener to menu components
    private void perfromAction() {
        saveFile.addActionListener(this);
        loadFile.addActionListener(this);

        addFolder.addActionListener(this);
        addNote.addActionListener(this);
        delete.addActionListener(this);
        recover.addActionListener(this);
        save.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: add action responding to different object
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveFile)) {
            saveFile();
        } else if (e.getSource().equals(loadFile)) {
            loadFile();
        } else if (e.getSource().equals(addFolder)) {
            addFolder();
        } else if (e.getSource().equals(addNote)) {
            addNote();
        } else if (e.getSource().equals(delete)) {
            deleteNote();
        } else if (e.getSource().equals(recover)) {
            recoverNote();
        } else if (e.getSource().equals(save)) {
            saveText();
        }
    }

    //MODIFES: this
    //EFFECTS: load Json file to this
    private void loadFile() {
        try {
            sync = jsonReader.read();
            management.readSync(sync);
            tree.readSync(sync);

            tree.updateTree();
            JOptionPane.showMessageDialog(frame, "Loaded from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: save this to Json file
    private void saveFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(sync);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Saved file to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: save text from myTextArea to noteNode object
    private void saveText() {
        String noteTitle = myTextField.getText();
        DefaultMutableTreeNode root = tree.getRoot();

        Note theNode = null;
        for (Enumeration e = root.depthFirstEnumeration(); e.hasMoreElements() && theNode == null;) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (noteTitle.equals(node.getUserObject())) {
                ((Note) node).setContent(myTextArea.getText());
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: move note from trash to folder
    private void recoverNote() {
        Folder to;
        String folderName = JOptionPane.showInputDialog(frame,
                "Please enter the name of notebook you want to recover to.", null);
        String noteTitle = JOptionPane.showInputDialog(frame,
                "Please enter the title of note you want to recover.", null);

        try {
            to = management.getFolder(folderName); //FolderNameNotFind
            management.recoverNote(noteTitle, to); //NoteNotExist
        } catch (FolderNotFind e) {
            JOptionPane.showMessageDialog(frame, "Folder not finds! Please enter again.");
        } catch (NoteNotFind e) {
            JOptionPane.showMessageDialog(frame, "Note not finds! Please enter again.");
        }

        tree.updateTree();
    }

    //MODIFIES: this
    //EFFECTS: move note from folder to trash
    private void deleteNote() {
        Folder from;
        String folderName = JOptionPane.showInputDialog(frame,
                "Please enter the name of notebook of the deleted note.", null);
        String noteTitle = JOptionPane.showInputDialog(frame,
                "Please enter the title of note you want to delete.", null);


        try {
            from = management.getFolder(folderName); //FolderNameNotFind
            management.deleteNote(noteTitle, from); //NoteNotExist
        } catch (FolderNotFind e) {
            JOptionPane.showMessageDialog(frame, "Folder not finds! Please enter again.");
        } catch (NoteNotFind e) {
            JOptionPane.showMessageDialog(frame, "Note not finds! Please enter again.");
        }

        tree.updateTree();

    }

    //MODIFIES: this
    //EFFECTS: add new noteNode
    private void addNote() {
        Folder folder;
        String folderName = JOptionPane.showInputDialog(frame,
                "Please enter the name of notebook.", null);
        String noteTitle = JOptionPane.showInputDialog(frame,
                "Please enter the title of note.", null);

        try {
            folder = management.getFolder(folderName); //throw FolderNotFind

            Note note = new Note(noteTitle, "Please enter content here...");
            folder.addNote(note);
        } catch (FolderNotFind e) {
            JOptionPane.showMessageDialog(frame, "Folder not finds! Please enter again.");
        }

        tree.updateTree();
    }

    //MODIFIES: this
    //EFFECTS: add new folderNode
    private void addFolder() {
        String folderName = JOptionPane.showInputDialog(frame,
                "Please enter the name of notebook.", null);

        try {
            management.addFolder(folderName);
        } catch (FolderExist e) {
            JOptionPane.showMessageDialog(frame, "Folder already exists! Please enter again.");
        }

        tree.updateTree();
    }
}
