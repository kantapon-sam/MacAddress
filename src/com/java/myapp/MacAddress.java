package com.java.myapp;

public class MacAddress {

    public static void main(String[] args) {
        Screen.setLAF();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                IPAddress ip = new IPAddress();
                ip.setVisible(true);
            }
        });
    }

}
