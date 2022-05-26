package ui;

import model.Event;
import model.EventLog;
import model.Management;
import model.Sync;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

import java.awt.event.*;

public class MyFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;


    private Sync sync;
    private Management management;

    private JFrame frame;
    private MyTree tree;
    private JSplitPane splitPane;

    private MyMenuBar myMenuBar;
    private MyTextField titleField;
    private MyTextArea contentField;


    //MODIFIES: this
    //EFFECTS: init frame, add other components to frame
    public MyFrame() {
        sync = new Sync();
        management = new Management(sync);

        titleField = new MyTextField();
        contentField = new MyTextArea();
        tree = new MyTree(sync,titleField, contentField);

        //create a frame
        this.frame = new JFrame("TakeYourNote App");
        this.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setLayout(new GridBagLayout());

        myMenuBar = new MyMenuBar(management, frame, tree, titleField, contentField);

        addComponentsToFrame(this.frame);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        printLog();
    }

    public void printLog() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: add other components to frame
    public void addComponentsToFrame(JFrame frame) {
        Container container = frame.getContentPane();
        container.setLayout(new GridBagLayout());

        frame.setJMenuBar(myMenuBar);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setMinimumSize(new Dimension(1000, 700));

        JScrollPane hierarchyWindow = getTreeInScrollPanel();

        splitPane.setLeftComponent(hierarchyWindow);

        JPanel mainWindow = new JPanel();
        mainWindow.add(titleField, getConstraintsForTextFld());
        mainWindow.add(getTextAreaInScrollPanel(), getConstraintsForTextArea());

        splitPane.setRightComponent(mainWindow);

        container.add(splitPane);

    }

    //MODIFIES: this
    //EFFECTS: add tree to JscollPane
    private JScrollPane getTreeInScrollPanel() {
        //Create the scroll pane and add the tree to it.
        JScrollPane hierarchyWindow = new JScrollPane(tree);

        return hierarchyWindow;
    }


    //MODIFES: this
    //EFFECTS: add TextArea to JScrollPane
    private JScrollPane getTextAreaInScrollPanel() {
        JScrollPane textAreaPanel = new JScrollPane(contentField);
        textAreaPanel.setPreferredSize(new Dimension(550, 500)); // wxh
        textAreaPanel.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        return textAreaPanel;
    }

    //EFFECTS: set the layout for TextField
    private GridBagConstraints getConstraintsForTextFld() {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(12, 0, 11, 11);
        return c;
    }

    //EFFECTS: set the layout for TextArea
    private GridBagConstraints getConstraintsForTextArea() {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 11, 11);
        return c;
    }

}

