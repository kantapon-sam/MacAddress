package com.java.myapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class IPAddress extends Form {

    public IPAddress() {
        MacAddress.addActionListener(new ActionListener() {
            String s = "";

            public void actionPerformed(ActionEvent e) {
                String mac0 = String.valueOf(Mac0.getText().toLowerCase());
                String mac1 = String.valueOf(Mac1.getText().toLowerCase());
                String mac2 = String.valueOf(Mac2.getText().toLowerCase());
                String mac3 = String.valueOf(Mac3.getText().toLowerCase());
                String mac4 = String.valueOf(Mac4.getText().toLowerCase());
                String mac5 = String.valueOf(Mac5.getText().toLowerCase());
                String macA = mac0 + ":" + mac1 + ":" + mac2 + ":" + mac3 + ":" + mac4 + ":" + mac5;
                String macB = mac0 + mac1 + "-" + mac2 + mac3 + "-" + mac4 + mac5;
                try {
                    if (file.getName().contains(".csv")) {

                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int lineNumber = 0;

                        while ((line = br.readLine()) != null) {
                            String mac = String.valueOf(Mac0.getText().toLowerCase());
                            String[] arr = line.split(",");
                            String[] arr2 = line.split(":");
                            String[] arr3 = macA.split(":");
                            String[] arr4 = line.split("-");
                            String[] arr5 = macB.split("-");
                            String S = "";
                            if (arr2.length == 6 && (arr3.length == 6 && macA.length() == 17)) {
                                S = macA.substring(3, macA.length() - 3);
                                arr[1] = arr[1].substring(3, arr[1].length() - 3);

                            } else if (arr4.length == 3 && (arr5.length == 3 && macB.length() == 14)) {
                                S = macB.substring(2, macB.length() - 2);
                                arr[1] = arr[1].substring(2, arr[1].length() - 2);
                            } else {
                                S = mac;
                            }
                            if (S.equals(arr[1])) {
                                lineNumber++;
                                if (lineNumber % 2 == 0) {
                                    s += arr[0] + "\n";
                                } else {
                                    s += arr[0];
                                    for (int j = 0; j < 25 - arr[0].length(); j++) {
                                        s += String.format("%s", "  ");
                                    }
                                }

                            }

                        }
                        String str = "file = " + file.getName();
                        String s0 = "";
                        for (int i = 0; i < 32 - str.length(); i++) {
                            s0 += String.format("%s", " ");
                        }
                        if (!s.equals("")) {
                            JOptionPane.showMessageDialog(null,
                                    "file = " + file.getName() + s0 + "Total = " + lineNumber + "\n" + s,
                                    "IP address",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                        s = "";

                        br.close();
                    } else {
                        String[] all = new String[10000];
                        int total = 0;
                        int t = 0;
                        int lineNumber = 0;
                        int[] numLine = new int[10000];
                        for (int i = 0; i < file.listFiles().length; i++) {
                            if (file.listFiles()[i].toString().contains(".csv")) {
                                total++;
                                BufferedReader br = new BufferedReader(new FileReader(file.listFiles()[i]));
                                String line;

                                while ((line = br.readLine()) != null) {
                                    String mac = String.valueOf(Mac0.getText().toLowerCase());
                                    String[] arr = line.split(",");
                                    String[] arr2 = line.split(":");
                                    String[] arr3 = macA.split(":");
                                    String[] arr4 = line.split("-");
                                    String[] arr5 = macB.split("-");
                                    String S = "";

                                    if (arr2.length == 6 && (arr3.length == 6 && macA.length() == 17)) {
                                        S = macA.substring(3, macA.length() - 3);
                                        arr[1] = arr[1].substring(3, arr[1].length() - 3);

                                    } else if (arr4.length == 3 && (arr5.length == 3 && macB.length() == 14)) {
                                        S = macB.substring(2, macB.length() - 2);
                                        arr[1] = arr[1].substring(2, arr[1].length() - 2);
                                    } else {
                                        S = mac;
                                    }
                                    if (S.equals(arr[1])) {
                                        lineNumber++;
                                        if (lineNumber % 2 == 0) {
                                            s += arr[0] + "\n";
                                        } else {
                                            s += arr[0];
                                            for (int j = 0; j < 25 - arr[0].length(); j++) {
                                                s += String.format("%s", "  ");
                                            }
                                        }

                                    }
                                    numLine[total - 1] = lineNumber;
                                }
                                lineNumber = 0;
                                all[total - 1] = "";
                                all[total - 1] += s;
                                br.close();
                                s = "";

                            }

                        }
                        String[] possibilities = new String[total];

                        for (File listFile : file.listFiles()) {
                            if (listFile.toString().contains(".csv")) {
                                possibilities[t] = listFile.getName();
                                t++;
                            }
                        }
                        String s = "";
                        try {
                            s = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Choose File",
                                    "Choose File",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    possibilities,
                                    "");
                        } catch (NullPointerException ex) {
                            Dialog.Nofile();
                        }
                        String str = "file = " + s;
                        String s0 = "";
                        for (int i = 0; i < 27 - str.length(); i++) {
                            s0 += String.format("%s", "  ");
                        }
                        for (int i = 0; i < possibilities.length; i++) {
                            if (s.equals(possibilities[i]) && !all[i].equals("")) {
                                JOptionPane.showMessageDialog(null,
                                        "file = " + s + s0 + " Total = " + numLine[i] + "\n" + all[i],
                                        "IP address",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    Dialog.Nofile();
                } catch (StringIndexOutOfBoundsException ex) {
                    Dialog.Invalidformat();
                }
            }

        });
        getContentPane().add(MacAddress);
    }
}
