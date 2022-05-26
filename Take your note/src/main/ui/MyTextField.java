package ui;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    private static final Font FONT_FOR_WIDGETS =
            new Font("SansSerif", Font.PLAIN, 16);

    //EFFECTS: init JTextField
    public MyTextField() {
        super("", 25);

        setEditable(false);
        setToolTipText(
                "Note title enters here");
        setFont(FONT_FOR_WIDGETS);
        setMargin(new Insets(5, 5, 5, 5)); // top, left, bottom, rt
    }

}
