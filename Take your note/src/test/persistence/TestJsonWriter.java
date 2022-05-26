package persistence;

import model.Folder;
import model.Note;
import model.Sync;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJsonWriter {
    Folder diary;
    Folder classnote;
    Note diary1;
    Note diary2;
    Note classnote1;
    Note demonote;
    Note trashnote;

    @BeforeEach
    void runBefore() {
        diary = new Folder("diary");
        classnote = new Folder("classnote");

        diary1 = new Note("2000-1-1", "Nothing to say...");
        diary2 = new Note("2019-1-1", "Today is a sunny day.");
        classnote1 = new Note("C2", "exception");
        demonote = new Note("test for demobook", "...");
        trashnote = new Note("test for trash", "...");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Sync sync = new Sync();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Sync sync = new Sync();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySync.json");
            writer.open();
            writer.write(sync);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySync.json");
            sync = reader.read();

            assertTrue(sync.folderExist("demobook"));
            assertTrue(sync.folderExist("trash"));

            Folder demobook = sync.getDemobook();
            Folder trash = sync.getTrash();

            assertEquals(0, demobook.getSize());
            assertEquals(0,trash.getSize());

            assertEquals(2, sync.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSync() {
        try {
            Sync sync = new Sync();

            Folder demobook = sync.getDemobook();
            Folder trash = sync.getTrash();

            diary.addNote(diary1);
            diary.addNote(diary2);
            classnote.addNote(classnote1);
            demobook.addNote(demonote);
            trash.addNote(trashnote);
            sync.addFolder(diary);
            sync.addFolder(classnote);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSync.json");
            writer.open();
            writer.write(sync);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSync.json");
            sync = reader.read();

            assertTrue(sync.folderExist("diary"));
            assertTrue(sync.folderExist("classnote"));
            assertEquals(4, sync.getSize());

            demobook = sync.getDemobook();
            trash = sync.getTrash();
            Folder diary = sync.getFolder("diary");
            Folder classnote = sync.getFolder("classnote");

            assertEquals(1, demobook.getSize());
            assertEquals(1, trash.getSize());
            assertEquals(2, diary.getSize());
            assertEquals(1, classnote.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
