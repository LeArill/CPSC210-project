package persistence;

import model.Folder;
import model.Sync;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJsonReader {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Sync sync = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySync.json");
        try {
            Sync sync = reader.read();

            assertTrue(sync.folderExist("demobook"));
            assertTrue(sync.folderExist("trash"));

            Folder demobook = sync.getDemobook();
            Folder trash = sync.getTrash();

            assertEquals(0, demobook.getSize());
            assertEquals(0,trash.getSize());

            assertEquals(2, sync.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSync.json");
        try {
            Sync sync = reader.read();

            assertTrue(sync.folderExist("diary"));
            assertTrue(sync.folderExist("classnote"));
            assertEquals(4, sync.getSize());

            Folder demobook = sync.getDemobook();
            Folder trash = sync.getTrash();
            Folder diary = sync.getFolder("diary");
            Folder classnote = sync.getFolder("classnote");

            assertEquals(1, demobook.getSize());
            assertEquals(1, trash.getSize());
            assertEquals(2, diary.getSize());
            assertEquals(1, classnote.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
