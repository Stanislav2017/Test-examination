package com.mykheikin;

import org.apache.commons.io.FilenameUtils;
import sun.swing.ImageIconUIResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestPanel extends JFrame {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 700;
    private Box main;
    private String authorName;
    private String extension;
    private String action;
    private Path sourceDir;
    private Path destinationDir;

    private JPanel sourcePanel;
    private JPanel destinationPanel;
    private JPanel serchPropertiesPanel;
    private JPanel actionPanel;
    private JPanel datePanel;
    private JPanel authorPanel;
    private JPanel extensionPanel;
    private JPanel buttonPanel;
    private JPanel rezultPanel;
    private List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public static void main(String[] args) {
        new TestPanel();
    }

    private TestPanel() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("File system work");

        this.createMainPanel();
        this.createTitle("File system work");

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
        this.sourcePanel = this.createPanel(true, sourceLabel, sourceTextField, sourceButton);

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
        this.destinationPanel = this.createPanel(
                true,
                destinationLabel,
                destinationTextField,
                destinationButton);

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
        this.extensionPanel = this.createPanel(true, extensioLabel, comboBox);

        JLabel rezultLabel = new JLabel("Search rezult :");
        JComboBox rezultComboBox = new JComboBox();
        JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    findFiles();
                    List<String> files = getFiles().stream()
                            .map(f -> FilenameUtils.getBaseName(f.getName()))
                            .collect(Collectors.toList());
                    for (String s : files) {
                        rezultComboBox.addItem(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        JButton actionButton = new JButton("Action");
        this.rezultPanel = this.createPanel(true, rezultLabel, rezultComboBox, findButton, actionButton);

        this.addToMainPanel(
                0,
                0,
                this.sourcePanel,
                this.destinationPanel,
                this.extensionPanel,
                this.rezultPanel);


        super.setVisible(true);
    }

    private void createMainPanel() {
        this.main = Box.createVerticalBox();
        super.add(this.main);
    }

    private void createTitle(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(title);
        label.setFont(new java.awt.Font("Times New Roman", 1, 24));
        panel.add(label);
        this.main.add(panel);
    }

    private JPanel createPanel(boolean isVisible, Component... components) {
        ButtonGroup radioButtonGroup = new ButtonGroup();
        ButtonGroup checkBoxGroup = new ButtonGroup();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setVisible(isVisible);
        for (Component component : components) {
            if (component instanceof JRadioButton) {
                radioButtonGroup.add((AbstractButton) component);
            }
            if (component instanceof JCheckBox) {
                checkBoxGroup.add((AbstractButton) component);
            }
            panel.add(component);
        }
        return panel;
    }

    private void addToMainPanel(Integer identTo, Integer identAfter, Component... components) {
        this.main.add(Box.createVerticalStrut(identTo));
        for (Component component : components) {
            this.main.add(component);
        }
    }

    private JFileChooser getChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    private void findFiles() throws IOException {
        setFiles(new FileServiceImpl().findFilesInDirWithExtension(this.sourceDir, this.extension));
    }
}
