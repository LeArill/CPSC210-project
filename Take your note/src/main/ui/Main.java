package ui;

import model.EventLog;

public class Main {

    public static void main(String[] args) {
        EventLog.getInstance().clear();
        new MyFrame();
    }
}
