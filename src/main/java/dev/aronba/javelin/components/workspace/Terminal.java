package dev.aronba.javelin.components.workspace;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.ui.FlatCaret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class Terminal extends JPanel {

    JScrollPane jScrollPane;
    JTextArea textArea;
    JTextField inputField;

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

        this.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
        this.add(inputField, BorderLayout.AFTER_LAST_LINE);
        this.add(jScrollPane, BorderLayout.CENTER);
    }


    private void actionListener(ActionEvent a) {
        textArea.append(">> " + inputField.getText() + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
        exec(inputField.getText());
        inputField.setText("");

    }

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
