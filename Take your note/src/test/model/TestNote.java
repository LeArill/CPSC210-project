package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestNote {
    private Note note;

    @BeforeEach
    void runBefore() {
        note = new Note("title", "content");
    }

    @Test
    void testNote() {
        assertEquals("title", note.getTitle());
        assertEquals("content", note.getContent());

        note.setTitle("diary");
        note.setContent("today is a sunny day.");
        assertEquals("diary", note.getTitle());
        assertEquals("today is a sunny day.", note.getContent());
    }
}
