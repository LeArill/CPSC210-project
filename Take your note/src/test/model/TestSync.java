package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSync {
    private Sync sync;

    @BeforeEach
    void runBefore() {
        sync = new Sync();
    }

    @Test
    void testDemobookAndTrash() {
        assertTrue(sync.folderExist("demobook"));
        assertTrue(sync.folderExist("trash"));
    }

}
