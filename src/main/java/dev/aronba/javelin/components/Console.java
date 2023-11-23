package dev.aronba.javelin.components;

import dev.aronba.javelin.Javelin;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console extends JScrollPane implements JavelinComponent {



    JTextArea textArea;
    public Console() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        this.add(textArea);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(600,600));

    }

    public Console(Process process) {
         textArea = new JTextArea();
         textArea.setEditable(false);
        this.add(textArea);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(600,600));
        input(process);
    }


    public void input(Process process) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                textArea.append(line + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
                revalidate();
                repaint();
            }
            int exitCode = process.waitFor();
            this.textArea.append("\nExternal program exited with code: " + exitCode + "\n");
            revalidate();
            repaint();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void setJavelinReference(Javelin javelin) {

    }
}
