package com.java.myapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Form extends JFrame {

    private JTable table;
    protected File file;
    protected JButton MacAddress;
    protected JTextField Mac;

    public Form() {
        super("Mac Address");
        setResizable(false);
        setSize(450, 170);
        setLocation(500, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Text Mac
        Mac = new JTextField();
        Mac.setBounds(62, 56, 162, 20);
        getContentPane().add(Mac);

        // Label Result
        final JLabel lblResult = new JLabel("Open File", JLabel.CENTER);
        lblResult.setBounds(2, 20, 270, 14);
        getContentPane().add(lblResult);

        // Table
        table = new JTable();
        getContentPane().add(table);

        JButton btnButton = new JButton("Open File");
        btnButton.setBounds(290, 17, 135, 23);

        MacAddress = new JButton("Mac Address");
        MacAddress.setBounds(290, 54, 135, 23);

        btnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("CSV file", "csv");
                fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileopen.setAcceptAllFileFilterUsed(false);
                fileopen.addChoosableFileFilter(filter);
                int ret = fileopen.showDialog(null, "Choose file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    // Read Text file

                    file = fileopen.getSelectedFile();
                    lblResult.setText(fileopen.getSelectedFile().toString());
                }

            }
        });
        getContentPane().add(btnButton);

        JButton IP_MAC = new JButton("CSV");
        IP_MAC.setBounds(290, 90, 135, 23);

        IP_MAC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fileError = -1;
                try {
                    Selectfile Select = new Selectfile();

                    try {
                        if (Select.getChooser().getSelectedFile().getName().contains(".txt")) {
                            new File(Select.getChooser().getSelectedFile().getPath().split(".txt")[0] + ".csv").delete();
                            BufferedReader br = new BufferedReader(new FileReader(Select.getFile()));
                            String pathOutput = Select.getFile().getPath().split("txt")[0] + "csv";
                            Sub(br, pathOutput);
                            Dialog.Success();
                        } else {
                            for (File fileFolder : Select.getFile().listFiles()) {
                                if (fileFolder.getName().contains(".csv")) {
                                    fileFolder.delete();
                                    fileError++;
                                }
                                if (fileFolder.getName().contains(".txt")) {
                                    BufferedReader br = new BufferedReader(new FileReader(fileFolder));
                                    String pathOutput = fileFolder.getPath().split("txt")[0] + "csv";
                                    fileError++;
                                    Sub(br, pathOutput);
                                }
                            }

                            Dialog.Success();
                        }
                    } catch (NullPointerException ex) {
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        if (Select.getFile().getName().contains(".txt")) {
                            Dialog.FileError(Select.getFile());
                        } else {
                            Dialog.FileError(Select.getFileFolder()[fileError].getName());
                        }
                    }
                } catch (NullPointerException ex) {
                }
            }

            private void Sub(BufferedReader br, String path) throws IOException {
                String line;
                String str;
                String all = "";
                int Number = 0;
                int end = 0;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split("\\.");

                    for (int i = 0; i < arr[0].length(); i++) {
                        if (arr[0].charAt(i) >= '0' && arr[0].charAt(i) <= '9') {
                            Number++;
                        } else {
                            Number = 0;
                        }
                    }
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == ' ' || line.charAt(i) == ',') {
                            break;
                        } else {
                            end++;
                        }
                    }
                    if (arr.length >= 4) {
                        String[] s = line.split(":");
                        if (s.length >= 5) {
                            str = line.substring(arr[0].length() - Number, end) + "," + line.substring(s[0].length() - 2);
                        } else {
                            str = line.substring(arr[0].length() - Number, end) + ",-";
                        }
                        String str2 = str.split(" ")[0];
                        all += (str2 + "\n");
                    }
                    Number = 0;
                    end = 0;
                }
                br.close();
                FileWriter IP_Mac;
                IP_Mac = new FileWriter(path, true);
                Writer.IP_Mac(IP_Mac, all);
            }
        });
        getContentPane().add(IP_MAC);
        JButton clear = new JButton("clear");
        clear.setBackground(new java.awt.Color(255, 0, 0));
        clear.setBounds(4, 55, 55, 22);
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mac.setText("");
            }
        });
        getContentPane().add(clear);
    }

}
