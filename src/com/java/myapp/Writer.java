package com.java.myapp;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void IP_Mac(FileWriter IP_Mac, String str) {
        try {
            IP_Mac.write(str);
            IP_Mac.close();
            System.out.println("Write Success");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
