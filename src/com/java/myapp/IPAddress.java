package com.java.myapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class IPAddress extends Form {

    public IPAddress() {
        MacAddress.addActionListener(new ActionListener() {
            String s = "";

            public void actionPerformed(ActionEvent e) {
                try {
                    if (file.getName().contains(".csv")) {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        MAC(br);

                        if (!s.equals("")) {
                            JOptionPane.showMessageDialog(null,
                                    "file = " + file.getName() + "\n" + s,
                                    "IP address",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                        s = "";

                        br.close();
                    } else {
                        int total = 0;
                        int t = 0;
                        String[] all = new String[1000];
                        for (int i = 0; i < file.listFiles().length; i++) {
                            if (file.listFiles()[i].toString().contains(".csv")) {
                                total++;
                                BufferedReader br = new BufferedReader(new FileReader(file.listFiles()[i]));
                                MAC(br);
                                all[total - 1] = "";
                                all[total - 1] += s;
                                br.close();
                                s = "";
                            }

                        }

                        String[] possibilities = new String[total];

                        for (int i = 0; i < file.listFiles().length; i++) {
                            if (file.listFiles()[i].toString().contains(".csv")) {
                                possibilities[t] = file.listFiles()[i].getName();
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

                        for (int i = 0; i < possibilities.length; i++) {
                            if (all[i].equals("")) {
                                JOptionPane.showMessageDialog(null,
                                        "NO IP address",
                                        "IP address",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            } else if (s.equals(possibilities[i])) {
                                JOptionPane.showMessageDialog(null,
                                        "file = " + s + "\n" + all[i],
                                        "IP address",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    Dialog.Nofile();
                }

            }

            private void MAC(BufferedReader br) throws IOException {
                String line;
                int lineNumber = 0;

                while ((line = br.readLine()) != null) {
                    String mac = String.valueOf(Mac.getText());
                    String[] arr = line.split(",");
                    String[] arr2 = line.split(":");
                    String S = "";
                    if (arr2.length == 6) {
                        S = mac.substring(3, mac.length() - 3);
                        arr[1] = arr[1].substring(3, arr[1].length() - 3);

                    }
                    if (S.equals(arr[1])) {
                        lineNumber++;
                        if (lineNumber % 2 == 0) {
                            s += arr[0] + "\n";
                        } else {
                            s += arr[0];
                            for (int i = 0; i < 25 - arr[0].length(); i++) {
                                s += String.format("%s", "  ");
                            }
                        }

                    }

                }
            }

        });
        getContentPane().add(MacAddress);

    }
}
