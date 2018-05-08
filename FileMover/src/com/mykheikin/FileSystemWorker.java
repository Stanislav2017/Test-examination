package com.mykheikin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemWorker extends JFrame {

    public static void main(String[] args) {
        new FileSystemWorker();
    }

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private Path sourceDir;
    private Path destinationDir;
    private String extension;
    private String action;
    private Box destinationBox;

    private FileSystemWorker() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("File system work");


        Box main = Box.createVerticalBox();
        main.add(Box.createVerticalStrut(30));
        getContentPane().add(main);

        JLabel sourceLabel = new JLabel("From :");
        JTextField sourceTextField = new JTextField(20);
        sourceLabel.setMaximumSize(new Dimension(150, 10));
        JButton sourceButton = new JButton("CHOOSE");
        sourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = getChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String sorceDir = chooser.getSelectedFile().toString() + "/";
                    sourceTextField.setText(sorceDir);
                    setSourceDir(sorceDir);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        Box sourceBox = Box.createHorizontalBox();
        sourceBox.add(Box.createHorizontalStrut(50));
        sourceBox.add(sourceLabel);
        sourceBox.add(Box.createHorizontalStrut(20));
        sourceBox.add(sourceTextField);
        sourceBox.add(Box.createHorizontalStrut(5));
        sourceBox.add(sourceButton);
        sourceBox.add(Box.createHorizontalStrut(50));
        main.add(sourceBox);
        main.add(Box.createVerticalStrut(30));

        JLabel destinationLabel = new JLabel("To :");
        JTextField destinationTextField = new JTextField();
        destinationTextField.setPreferredSize(new Dimension(100, 20));
        JButton destinationButton = new JButton("CHOOSE");
        destinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = getChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String destinationDir = chooser.getSelectedFile().toString() + "/";
                    destinationTextField.setText(destinationDir);
                    setDestinationDir(destinationDir);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        destinationBox = Box.createHorizontalBox();
        destinationBox.add(Box.createHorizontalStrut(50));
        destinationBox.add(destinationLabel);
        destinationBox.add(Box.createHorizontalStrut(37));
        destinationBox.add(destinationTextField);
        destinationBox.add(Box.createHorizontalStrut(5));
        destinationBox.add(destinationButton);
        destinationBox.add(Box.createHorizontalStrut(50));
        main.add(destinationBox);
        main.add(Box.createVerticalStrut(30));

        JLabel actionLabel = new JLabel("Action :");
        JRadioButton copyJRadioButton = this.createJRadioButton("Copy");
        JRadioButton moveJRadioButton = this.createJRadioButton("Move");
        JRadioButton deleteJRadioButton = this.createJRadioButton("Delete");

        ButtonGroup group = new ButtonGroup();
        group.add(copyJRadioButton);
        group.add(moveJRadioButton);
        group.add(deleteJRadioButton);

        Box radioButtonBox = Box.createHorizontalBox();
        radioButtonBox.add(Box.createHorizontalStrut(50));
        radioButtonBox.add(actionLabel);
        radioButtonBox.add(Box.createHorizontalGlue());
        radioButtonBox.add(copyJRadioButton);
        radioButtonBox.add(Box.createHorizontalStrut(15));
        radioButtonBox.add(moveJRadioButton);
        radioButtonBox.add(Box.createHorizontalStrut(15));
        radioButtonBox.add(deleteJRadioButton);
        radioButtonBox.add(Box.createHorizontalGlue());
        radioButtonBox.add(Box.createHorizontalStrut(50));
        main.add(radioButtonBox);
        main.add(Box.createVerticalStrut(30));


        JLabel extensioLabel = new JLabel("File extension :");
        String[] items = {"PDF", "JPEG", "JAVA", "DOC", "DOCX","AVI", "PNG", "TXT", "JPG", "TXT", "ODT", "AVI"};
        JComboBox comboBox = new JComboBox(items);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String extension = comboBox.getSelectedItem().toString().toLowerCase();
                setExtension(extension);
            }
        });

        Box extensionBox = Box.createHorizontalBox();
        extensionBox.add(Box.createHorizontalStrut(50));
        extensionBox.add(extensioLabel);
        extensionBox.add(Box.createHorizontalGlue());
        extensionBox.add(comboBox);
        extensionBox.add(Box.createHorizontalGlue());
        main.add(extensionBox);
        main.add(Box.createVerticalStrut(30));

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                executeAction();
            }
        });

        Box executeBox = Box.createHorizontalBox();
        executeBox.add(Box.createHorizontalGlue());
        executeBox.add(executeButton);
        executeBox.add(Box.createHorizontalGlue());
        main.add(executeBox);
        main.add(Box.createVerticalStrut(30));


        super.setVisible(true);
    }

    private JRadioButton createJRadioButton(String name) {
        JRadioButton jRadioButton = new JRadioButton(name);
        jRadioButton.setMnemonic(KeyEvent.VK_D);
        jRadioButton.setActionCommand(name);
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ((name.toUpperCase()).equals("DELETE")) {
                    destinationBox.setVisible(false);
                } else {
                    destinationBox.setVisible(true);
                }
                String action = jRadioButton.getText().toUpperCase();
                setAction(action);
            }
        });
        return jRadioButton;
    }

    private JFileChooser getChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }


    private void executeAction() {
        FileService fileService = new FileServiceImpl();
        switch (getAction()) {
            case "DELETE":
                try {
                    fileService.deleteFilesWithExtension(getSourceDir(), getExtension());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case "MOVE":
                try {
                    fileService.move(getSourceDir(), getDestinationDir(), getExtension());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case "COPY":
                try {
                    fileService.copy(getSourceDir(), getDestinationDir(), getExtension());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Path getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = Paths.get(sourceDir);
    }

    public Path getDestinationDir() {
        return destinationDir;
    }

    public void setDestinationDir(String destinationDir) {
        this.destinationDir = Paths.get(destinationDir);
    }
}
