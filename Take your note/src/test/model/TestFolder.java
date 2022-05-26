package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestFolder {
    Folder notebook;
    Note note1;
    Note note2;
    Note note3;

    @BeforeEach
    void runBefore() {
        notebook = new Folder("notebook");
        note1 = new Note("title1", "content1");
        note2 = new Note("title2", "content2");
        note3 = new Note("title3", "content3");
    }

    @Test
    void testaddAndDelete() {
        String title = note1.getTitle();

        notebook.addNote(note1);
        assertTrue(notebook.noteExist(title));

        notebook.deleteNote(note1);
        assertFalse(notebook.noteExist(title));
    }

    @Test
    void testgetNote() {
        String titleExist = note1.getTitle();
        String titleNotExist = note2.getTitle();
        Note note;

        notebook.addNote(note1);

        note = notebook.getNote(titleExist);
        assertEquals(note1, note);

        note = notebook.getNote(titleNotExist);
        assertNull(note);
    }
}
