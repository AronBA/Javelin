package dev.aronba.javelin.components;

import dev.aronba.javelin.Project;
import dev.aronba.javelin.components.workspace.TextEditor;
import dev.aronba.javelin.components.workspace.Workspace;
import dev.aronba.javelin.util.FileUtil;
import dev.aronba.javelin.Javelin;
import dev.aronba.javelin.util.JavelinSize;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Optional;

public class StartMenu extends JPanel   {

    Javelin javelin;
    JButton openProjectButton;
    JButton openVSCButton;


    public StartMenu(Javelin javelin){
        this.javelin = javelin;

        javelin.setSize(JavelinSize.SMALL);

        this.setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        Image image = icon.getImage();
        Image resize = image.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel("Javelin IDE", new ImageIcon(resize),SwingConstants.TRAILING);
        titleLabel.setFont(new Font("Arial",Font.BOLD,40));
        this.add(titleLabel, BorderLayout.CENTER);


        openProjectButton = new JButton("Open Project");
        openProjectButton.addActionListener( actionEvent-> openProject());

        openVSCButton = new JButton("Open from Vsc");
        openVSCButton.setEnabled(false);
        openVSCButton.addActionListener(actionEvent -> getProject());


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout(5,5));

        southPanel.add(openVSCButton, BorderLayout.SOUTH);
        southPanel.add(openProjectButton, BorderLayout.NORTH);

        this.add(southPanel, BorderLayout.SOUTH);
    }

    private void openProject(){
        Optional<File> folder = FileUtil.openFolder();
        if (folder.isEmpty()) return;
        javelin.setFullScreen();

        javelin.setCurrentComponent(new Workspace(new Project(folder.get())));
    }
    private void newProject(){

    }
    private void getProject(){

    }


}
