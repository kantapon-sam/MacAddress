package com.java.myapp;

import javax.swing.UIManager;

public class Screen {

    public static void setLAF() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to set LookAndFeel");

        }
    }
}
