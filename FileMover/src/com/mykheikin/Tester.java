package com.mykheikin;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class Tester extends JFrame {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 500;
    private String authorName;
    private String extension;
    private String action;
    private Path sourceDir;
    private Path destinationDir;
    private Long startTime;
    private Long finishTime;
    private Box main;
    private Map<String, JPanel> panels = new TreeMap<>();


    private JPanel sourcePanel;
    private JPanel destinationPanel;
    private JPanel serchPropertiesPanel;
    private JPanel actionPanel;
    private JPanel datePanel;
    private JPanel authorPanel;
    private JPanel extensionPanel;
    private JPanel buttonPanel;

    private boolean isDateSelected;
    private boolean isExtensionSelected;
    private boolean isAuthorSelected;

    private Tester() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("File system work");

        this.createTitleToMainPanel("File system worker");

        JLabel sourceLabel = new JLabel("Source folder :");
        JTextField sourceTextField = new JTextField(20);
        JButton sourceButton = new JButton("Choose");
        sourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = getChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String destinationDir = fileChooser.getSelectedFile().toString() + "/";
                    setSourceDir(destinationDir);
                    sourceTextField.setText(destinationDir);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        this.sourcePanel = this.createPanelWithComponents(true, sourceLabel, sourceTextField, sourceButton);
        this.addToMainPanel(0,0, this.sourcePanel);

        JLabel destinationLabel = new JLabel("Distination folder :");
        JTextField destinationTextField = new JTextField(18);
        JButton destinationButton = new JButton("Choose");
        destinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = getChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String destinationDir = fileChooser.getSelectedFile().toString() + "/";
                    setDestinationDir(destinationDir);
                    destinationTextField.setText(destinationDir);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        this.destinationPanel = this.createPanelWithComponents(
                true,
                destinationLabel,
                destinationTextField,
                destinationButton);
        this.addToMainPanel(0,0, this.destinationPanel);

        JLabel searchProperties = new JLabel("Search properties :");
        JCheckBox dateCheckBox = new JCheckBox("Date");
        dateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isDateSelected = dateCheckBox.isSelected();
                if (dateCheckBox.isSelected()) {
                    datePanel.setVisible(true);
                } else {
                    datePanel.setVisible(false);
                }
            }
        });
        JCheckBox authotCheckBox = new JCheckBox("Author");
        authotCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isAuthorSelected = authotCheckBox.isSelected();
                if (authotCheckBox.isSelected()) {
                    authorPanel.setVisible(true);
                } else {
                    authorPanel.setVisible(false);
                }
            }
        });
        JCheckBox extensionCheckBox = new JCheckBox("Extension");
        extensionCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isExtensionSelected = extensionCheckBox.isSelected();
                if (extensionCheckBox.isSelected()) {
                   extensionPanel.setVisible(true);
                } else {
                    extensionPanel.setVisible(false);
                }
            }
        });
        this.serchPropertiesPanel = this.createPanelWithComponents(
                true,
                searchProperties,
                dateCheckBox,
                authotCheckBox,
                extensionCheckBox);
        this.main.add(this.serchPropertiesPanel);

        JLabel actionLabel = new JLabel("Action :");
        JRadioButton copyJRadioButton = this.createJRadioButton("Copy");
        JRadioButton moveJRadioButton = this.createJRadioButton("Move");
        JRadioButton deleteJRadioButton = this.createJRadioButton("Delete");
        this.actionPanel = this.createPanelWithComponents(
                true,
                actionLabel,
                copyJRadioButton,
                moveJRadioButton,
                deleteJRadioButton);
        this.main.add(this.actionPanel);

        JLabel startTimeLabel = new JLabel("Start day :");
        JXDatePicker dateStartPicker = new JXDatePicker();
        dateStartPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Long startTime = dateStartPicker.getDate().getTime();
                setStartTime(startTime);
            }
        });
        dateStartPicker.setDate(Calendar.getInstance().getTime());
        dateStartPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));

        JLabel finishTimeLabel = new JLabel("Finish day :");
        JXDatePicker dateFinishPicker = new JXDatePicker();
        dateFinishPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Long finishTime = dateFinishPicker.getDate().getTime();
                setFinishTime(finishTime);
            }
        });
        dateFinishPicker.setDate(Calendar.getInstance().getTime());
        dateFinishPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        this.datePanel = this.createPanelWithComponents(
                false,
                startTimeLabel,
                dateStartPicker,
                finishTimeLabel,
                dateFinishPicker);
        this.main.add(this.datePanel);

        JLabel authorLabel = new JLabel("File author :");
        JTextField authorTextField = new JTextField(20);
        this.authorPanel = this.createPanelWithComponents(false, authorLabel, authorTextField);
        this.main.add(this.authorPanel);

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
        this.extensionPanel = this.createPanelWithComponents(false, extensioLabel, comboBox);
        this.main.add(this.extensionPanel);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                executeAction();
            }
        });
        JButton cancelButton = new JButton("Clean");
        this.buttonPanel = this.createPanelWithComponents(true, executeButton, cancelButton);
        this.main.add(this.buttonPanel);

        super.setVisible(true);
    }

    private void createTitleToMainPanel(String title) {
        this.main = Box.createVerticalBox();
        super.add(main);
        this.main.add(Box.createVerticalStrut(0));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(title);
        label.setFont(new java.awt.Font("Times New Roman", 1, 24));
        panel.add(label);
        this.main.add(panel);
        this.main.add(Box.createVerticalStrut(0));
    }

    private JRadioButton createJRadioButton(String name) {
        JRadioButton jRadioButton = new JRadioButton(name);
        jRadioButton.setMnemonic(KeyEvent.VK_D);
        jRadioButton.setActionCommand(name);
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String action = jRadioButton.getText().toUpperCase();
                if (action.equals("MOVE") || action.equals("COPY")) {
                    destinationPanel.setVisible(true);
                } else {
                    destinationPanel.setVisible(false);
                }
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

    private JPanel createPanelWithComponents(boolean isVisible, Component...components) {
        JPanel panel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        panel.setVisible(isVisible);
        for (Component component : components) {
            if (component instanceof JRadioButton) {
                group.add((AbstractButton) component);
            }
            panel.add(component);
        }
        return panel;
    }

    private void addToMainPanel(Integer identTo, Integer identAfter, JPanel panel) {
        this.main.add(panel);
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isDateSelected() {
        return isDateSelected;
    }

    public void setDateSelected(boolean dateSelected) {
        isDateSelected = dateSelected;
    }

    public boolean isExtensionSelected() {
        return isExtensionSelected;
    }

    public void setExtensionSelected(boolean extensionSelected) {
        isExtensionSelected = extensionSelected;
    }

    public boolean isAuthorSelected() {
        return isAuthorSelected;
    }

    public void setAuthorSelected(boolean authorSelected) {
        isAuthorSelected = authorSelected;
    }

    public static void main(String[] args) {
        new Tester();
    }
}

