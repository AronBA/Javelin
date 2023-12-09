package dev.aronba.javelin.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * The Terminal class represents a component in the Javelin IDE for executing commands and displaying output.
 */
public class Terminal extends JPanel {

    /**
     * The scroll pane for the text area.
     */
    JScrollPane jScrollPane;

    /**
     * The text area for displaying output.
     */
    JTextArea textArea;

    /**
     * The input field for entering commands.
     */
    JTextField inputField;

    /**
     * Constructs a Terminal object with an input field and a text area.
     */
    public Terminal() {
        this.setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.addActionListener(this::actionListener);
        inputField.setBorder(BorderFactory.createEmptyBorder());
        inputField.setToolTipText("This is your inputField");

        textArea = new JTextArea();
        textArea.setText("Hello world :) \n");
        textArea.setEditable(false);
        textArea.setBackground(new Color(0x1F2228));
        textArea.setFocusable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setToolTipText("This is the output field (Read only)");
        textArea.setAutoscrolls(true);

        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        this.add(inputField, BorderLayout.AFTER_LAST_LINE);
        this.add(jScrollPane, BorderLayout.CENTER);
    }

    /**
     * Action listener for the input field. Appends the entered command to the text area,
     * executes the command, and clears the input field.
     *
     * @param a The ActionEvent triggered by pressing Enter in the input field.
     */
    private void actionListener(ActionEvent a) {
        textArea.append(">> " + inputField.getText() + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
        exec(inputField.getText());
        inputField.setText("");
    }

    /**
     * Executes the specified command in the system's command prompt and appends the output to the text area.
     *
     * @param command The command to execute.
     */
    private void exec(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true);
        try {

            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                textArea.append(line + "\n");
            }

            process.waitFor();
            textArea.setCaretPosition(textArea.getDocument().getLength());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            textArea.append(e.getMessage());
        }
    }

}

