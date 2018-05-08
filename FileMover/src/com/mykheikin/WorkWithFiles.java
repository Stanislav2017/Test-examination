package com.mykheikin;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WorkWithFiles extends JFrame {

    public static void main(String[] args) {
        new WorkWithFiles();
    }

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private Long startTime;
    private Long finishTime;
    private String sourcePath;
    private String destinationPath;
    private String extension;
    private String action;

    private WorkWithFiles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        super.setResizable(false);
        super.setTitle("Work with files");

        Box main = Box.createVerticalBox();
        main.add(Box.createVerticalStrut(20));

        JLabel sourceLabel = new JLabel("Folder from :");
        JLabel destinationLabel = new JLabel("    Folder to :");
        JLabel dateStartLabel = new JLabel("Date start :");
        JLabel dateFinishLabel = new JLabel("Date finish :");

        JTextField sourceTextField = new JTextField();
        JTextField destinationTextField = new JTextField();

        JButton sourceButton = new JButton("Choose");
        JButton destinationButton = new JButton("Choose");

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

        Box sourceBox = Box.createHorizontalBox();
        sourceBox.add(Box.createHorizontalStrut(50));
        sourceBox.add(sourceLabel);
        sourceBox.add(Box.createHorizontalStrut(10));
        sourceBox.add(sourceTextField);
        sourceBox.add(sourceButton);
        sourceBox.add(Box.createHorizontalStrut(50));
        main.add(sourceBox);
        main.add(Box.createVerticalStrut(20));

        Box destinationBox = Box.createHorizontalBox();
        destinationBox.add(Box.createHorizontalStrut(50));
        destinationBox.add(destinationLabel);
        destinationBox.add(Box.createHorizontalStrut(10));
        destinationBox.add(destinationTextField);
        destinationBox.add(destinationButton);
        destinationBox.add(Box.createHorizontalStrut(50));
        main.add(destinationBox);
        main.add(Box.createVerticalStrut(20));

        Box dateStartBox = Box.createHorizontalBox();
        dateStartBox.add(Box.createHorizontalGlue());
        dateStartBox.add(dateStartLabel);
        dateStartBox.add(Box.createHorizontalStrut(10));
        dateStartBox.add(dateStartPicker);
        dateStartBox.add(Box.createHorizontalGlue());
        main.add(dateStartBox);
        main.add(Box.createVerticalStrut(10));

        Box dateFinishBox = Box.createHorizontalBox();
        dateFinishBox.add(Box.createHorizontalGlue());
        dateFinishBox.add(dateFinishLabel);
        dateFinishBox.add(Box.createHorizontalStrut(10));
        dateFinishBox.add(dateFinishPicker);
        dateFinishBox.add(Box.createHorizontalGlue());
        main.add(dateFinishBox);
        main.add(Box.createVerticalStrut(20));



        super.add(main);

        super.setVisible(true);
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

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
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
}
