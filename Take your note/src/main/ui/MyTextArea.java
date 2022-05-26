package ui;

import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea {
    private static final Font FONT_FOR_EDITOR =
            new Font("Comic Sans MS", Font.PLAIN, 16);

    //EFFECTS: init JTextArea
    public MyTextArea() {
        super("");

        setToolTipText("Note text (optional)");
        setFont(FONT_FOR_EDITOR);
        setLineWrap(true);
        setWrapStyleWord(true);
        setMargin(new Insets(5, 5, 5, 5)); // top, left, bottom, rt

    }
}
