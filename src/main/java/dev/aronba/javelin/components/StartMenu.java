package dev.aronba.javelin.components;

import dev.aronba.javelin.util.FileIO;
import dev.aronba.javelin.Javelin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Optional;

public class StartMenu extends JPanel implements JavelinComponent {

    Javelin javelin;
    JButton openFileButton;
    JButton openProjectButton;
    JButton openVSCButton;


    public StartMenu(){

        this.setLayout(new BorderLayout(10,10));
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        Image image = icon.getImage();
        Image resize = image.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel("Javelin IDE", new ImageIcon(resize),SwingConstants.TRAILING);
        titleLabel.setFont(new Font("Arial",Font.BOLD,40));
        this.add(titleLabel, BorderLayout.CENTER);


        openFileButton  = new JButton("Open File");
        openFileButton.addActionListener(a -> {
            Optional<File> file = FileIO.open();
            if (file.isEmpty()) return;

            javelin.setFullScreen();
            javelin.setCurrentComponent(new TextEditor(javelin.getSize(), file.get()));

        });
        openProjectButton = new JButton("Open Project");
        openProjectButton.setEnabled(false);

        openVSCButton = new JButton("Open from Vsc");
        openVSCButton.setEnabled(false);


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout(5,5));

        southPanel.add(openVSCButton, BorderLayout.SOUTH);
        southPanel.add(openFileButton, BorderLayout.CENTER);
        southPanel.add(openProjectButton, BorderLayout.NORTH);

        this.add(southPanel, BorderLayout.SOUTH);
    }



    public void setJavelinReference(Javelin javelin) {
        this.javelin = javelin;
    }

}
