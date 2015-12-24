package com.java.myapp;

import java.awt.EventQueue;

public class MacAddress {

    public static void main(String[] args) {
        Screen.setLAF();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                IPAddress ip = new IPAddress();
                ip.setVisible(true);
            }
        });
    }

}
