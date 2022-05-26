package ui;

import exceptions.FolderExist;
import exceptions.FolderNotFind;
import exceptions.NoteNotFind;
import model.Folder;
import model.Note;
import model.Management;
import model.Sync;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TakeYourNoteApp {
    private static final String JSON_STORE = "./data/takeyournoteapp.json";
    private Management management;
    private Sync sync;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the TakeYourNote application
    public TakeYourNoteApp() {
        runTakeYourNoteApp();
    }

    //reference from TellerApp
    //MODIFIES: this
    //EFFECTS: processes user input
    public void runTakeYourNoteApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu(); //print the menu
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("s")) {
                saveApp();
            } else if (command.equals("l")) {
                loadApp();
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }


    //reference from TellerApp
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        try {
            if (command.equals("a")) {
                addNote();
            } else if (command.equals("b")) {
                buildFolder();
            } else if (command.equals("d")) {
                deleteNote();
            } else if (command.equals("r")) {
                recoverNote();
            } else if (command.equals("h")) {
                searchNote();
            } else {
                System.out.println("Selection not valid...");
            }
        } catch (FolderNotFind e) {
            System.out.println(e.getMessage() + ", please enter the name again");
        } catch (FolderExist e) {
            System.out.println(e.getMessage() + ", please enter the name again");
        } catch (NoteNotFind e) {
            System.out.println(e.getMessage() + ", please enter the title again");
        }
    }

    //EFFECTS: save data to file
    private void saveApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(sync);
            jsonWriter.close();
            System.out.println("Saved file to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: load data to file
    private void loadApp() {
        try {
            sync = jsonReader.read();
            management.readSync(sync);

            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: folder
    //EFFECTS: add a note to folder
    private void addNote() throws FolderNotFind {
        Folder folder;
        String folderName;
        String title;
        String content;


        System.out.println("Choose notebook to add:\n");
        folderName = input.next();

        folder = management.getFolder(folderName); //throw FolderNameNotFind

        System.out.println("Write down your title:\n");
        title = input.next();
        System.out.println("Write down your note:\n");
        content = input.next();

        Note note = new Note(title, content);
        folder.addNote(note);
    }

    //MODIFIES: this
    //EFFECTS: create a new folder and add it to sync
    private void buildFolder() throws FolderExist {
        String folderName;

        System.out.println("create a name for your new notebook:\n");
        folderName = input.next();

        management.addFolder(folderName); //throw FolderNameExist

    }

    //MODIFIES: folder
    //EFFECTS: move note from folder to trash
    private void deleteNote() throws FolderNotFind, NoteNotFind {
        Folder from;
        String folderName;
        String title;

        System.out.println("Please enter the name of notebook you want to check:");
        printFolders();
        folderName = input.next();

        from = management.getFolder(folderName); //FolderNameNotFind
        printNotes(from);

        System.out.println("Please enter the title of note you want to delete:");
        title = input.next();

        management.deleteNote(title, from); //NoteNotExist
        System.out.println("Your note was moved");
    }

    //MODIFIES: folder
    //EFFECTS: move note from trash to folder
    private void recoverNote() throws FolderNotFind, NoteNotFind {
        Folder to;
        String folderName;
        String title;

        Folder trash = sync.getTrash();

        System.out.println("Please enter the title of note you want to recover:");
        printNotes(trash);
        title = input.next();

        System.out.println("Please enter the notebook you want to recover to:");
        folderName = input.next();

        to = management.getFolder(folderName); //FolderNameNotFind

        management.recoverNote(title, to); //NoteNotExist
        System.out.println("Your note was recovered");
    }

    //MODIFIES: this
    //EFFECTS: search note content given title
    private void searchNote() throws FolderNotFind, NoteNotFind {
        String fileName;
        String title;
        String content;

        System.out.println("Please enter the notebook you want to search:");
        printFolders();
        fileName = input.next();

        Folder folder = management.getFolder(fileName);

        System.out.println("Please enter the title of note you want to search");
        printNotes(folder);
        title = input.next();

        content = management.searchNote(title, folder);

        System.out.println(content);
    }

    //EFFECTS: print the name of folders
    private void printFolders() {
        List<Folder> folders = sync.getHierarchy();
        for (Folder f: folders) {
            System.out.println(f.getFolderName());
        }
    }

    //EFFECTS: print the title of notes in folder
    private void printNotes(Folder folder) {
        List<Note> notes = folder.getNotes();
        System.out.println(folder.getFolderName() + ":");

        Note note;

        for (int i = 0; i < notes.size(); i++) {
            note = notes.get(i);

            System.out.println(note.getTitle());
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add your note");
        System.out.println("\tb -> add your notebook");
        System.out.println("\td -> delete your note");
        System.out.println("\tr -> recover your note");
        System.out.println("\th -> search your note");
        System.out.println("\ts -> save your file");
        System.out.println("\tl -> load your file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: init settings
    private void init() {
        sync = new Sync();
        management = new Management(sync);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


}
