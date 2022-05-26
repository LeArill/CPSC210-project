package model;

import exceptions.FolderExist;
import exceptions.FolderNotFind;
import exceptions.NoteNotFind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestManagement {
    Management management;
    Sync sync;
    Note note1;
    Note note2;
    Note note3;

    @BeforeEach
    void runBefore() {
        sync = new Sync();
        management = new Management(sync);

        note1 = new Note("title1", "content1");
        note2 = new Note("title2", "content2");
        note3 = new Note("title3", "content3");
    }


    @Test
    void testaddFolderNoException() {
        String folderName = "cpsc210";

        try {
            assertFalse(sync.folderExist(folderName));
            management.addFolder(folderName);
            assertTrue(sync.folderExist(folderName));
        } catch (FolderExist e) {
            fail("folder name" + folderName + "should have worked");
        }
    }

    @Test
    void testaddFolderWithException() {
        String folderName = "cpsc210";
        Folder folder = new Folder(folderName);
        sync.addFolder(folder);

        try {
            assertTrue(sync.folderExist(folderName));
            management.addFolder(folderName);
        } catch (FolderExist e) {
            //good
        }
    }

    @Test
    void testdeleteNoteNoException() {
        String title = note1.getTitle();
        Folder demobook = sync.getDemobook();
        demobook.addNote(note1);

        try {
            management.deleteNote(title, demobook);
        } catch (NoteNotFind e) {
            fail("Note title" + title + "should have worked");
        }

        Folder trash = sync.getTrash();

        assertTrue(trash.noteExist(title));
        assertFalse(demobook.noteExist(title));
    }

    @Test
    void testdeleteNoteWithException() {
        String note1Title = note1.getTitle();
        String note2Title = note2.getTitle();
        Folder demobook = sync.getDemobook();
        demobook.addNote(note1);

        try {
            assertTrue(demobook.noteExist(note1Title));
            assertFalse(demobook.noteExist(note2Title));
            management.deleteNote(note2Title, demobook);
        } catch (NoteNotFind e) {
            //good
        }
    }

    @Test
    void testrecoverNoteNoException() {
        String title = note1.getTitle();
        Folder demobook = sync.getDemobook();
        Folder trash = sync.getTrash();
        trash.addNote(note1);

        try {
            assertFalse(demobook.noteExist(title));
            assertTrue(trash.noteExist(title));
            management.recoverNote(title, demobook);
            assertTrue(demobook.noteExist(title));
            assertFalse(trash.noteExist(title));
        } catch (NoteNotFind e) {
            fail("Note title" + title + "should have worked");
        }
    }

    @Test
    void testrecoverNoteWithException() {
        String title = "nothing";
        Folder trash = sync.getTrash();
        Folder demobook = sync.getDemobook();

        try {
            management.recoverNote(title, demobook);
        } catch (NoteNotFind e) {
            //good
        }

        assertFalse(trash.noteExist(title));
    }

    @Test
    void testgetFolderNoException() {
        String folderName = "cpsc210";

        try {
            management.addFolder(folderName);
            management.getFolder(folderName);
        } catch (FolderNotFind e) {
            fail(folderName + " should have worked");
        } catch (FolderExist e) {
            e.printStackTrace();
        }
    }

    @Test
    void testgetFolderWithException() {
        String folderName = "cpsc210";

        try {
            management.getFolder(folderName);
        } catch (FolderNotFind e) {
            //good
        }

        assertFalse(sync.folderExist(folderName));
    }

    @Test
    void testsearchNoteNoException() {
        String title = note1.getTitle();
        String content;

        Folder demobook = sync.getDemobook();
        demobook.addNote(note1);

        try {
            content = management.searchNote(title, demobook);
            assertEquals(content, note1.getContent());
        } catch (NoteNotFind e) {
            fail(title + "should have worked");
        }
    }

    @Test
    void testsearchNoteWithException() {
        String title = "nothing";
        String content;

        Folder demobook = sync.getDemobook();

        try {
            content = management.searchNote(title, demobook);
        } catch (NoteNotFind e) {
            //good
        }
    }

    @Test
    void testReadSync() {
        Sync newSync = new Sync();
        management.readSync(newSync);

        assertEquals(newSync, management.getSync());
    }

}
