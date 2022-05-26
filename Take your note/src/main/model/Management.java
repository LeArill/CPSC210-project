package model;

import exceptions.FolderExist;
import exceptions.FolderNotFind;
import exceptions.NoteNotFind;

import java.util.List;

/*
warning:
    Folder name can't repeat
    Note name can repeat

    default Folders in sync.hierarchy: demobook, trash

 */
public class Management {
    private Sync sync;

    //EFFECTS: construct Management
    public Management(Sync sync) {
        this.sync = sync;
    }

    //MODIFES: this
    //EFFECTS: create a folder and add the folder to this
    public void addFolder(String folderName) throws FolderExist {
        if (sync.folderExist(folderName)) {
            throw new FolderExist("Folder name already exists");
        }

        Folder folder = new Folder(folderName);
        sync.addFolder(folder);
        EventLog.getInstance().logEvent(new Event("Folder " + folderName + " has been added."));
    }

    //MODIFIES: folder, this
    //EFFECTS: move note from folder to trash
    public void deleteNote(String title, Folder from) throws NoteNotFind {
        if (!from.noteExist(title)) {
            throw new NoteNotFind("No note title called " + title);
        } else {
            Note note = from.getNote(title);
            Folder trash = sync.getTrash();

            from.deleteNote(note);
            trash.addNote(note);
        }
    }

    //MODIFIES: folder, this
    //EFFECTS: use title to recover note from trash to a folder
    public void recoverNote(String title, Folder to) throws NoteNotFind {
        Folder trash = sync.getTrash();


        if (!trash.noteExist(title)) {
            throw new NoteNotFind("No note title called " + title);
        } else {
            Note note = trash.getNote(title);
            trash.deleteNote(note);
            to.addNote(note);
        }
    }

    //MODIFIES: this
    //EFFECTS: get folder
    public Folder getFolder(String folderName) throws FolderNotFind {
        if (!sync.folderExist(folderName)) {
            throw new FolderNotFind("Not find folder called " + folderName);
        } else {
            return sync.getFolder(folderName);
        }
    }

    //MODIFIES: this
    //EFFECTS: get content
    public String searchNote(String title, Folder folder) throws NoteNotFind {
        Note note;
        String content;

        if (!folder.noteExist(title)) {
            throw new NoteNotFind("No note title called " + title);
        } else {
            note = folder.getNote(title);
            content = note.getContent();
            return content;
        }
    }

    //MODIFIES: this
    //EFFECTS: reload sync to management
    public void readSync(Sync sync) {
        this.sync = sync;
    }

    //EFFECTS: get sync
    public Sync getSync() {
        return sync;
    }

}
