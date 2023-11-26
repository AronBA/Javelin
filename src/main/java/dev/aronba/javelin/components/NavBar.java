package dev.aronba.javelin.components;




import dev.aronba.javelin.Javelin;
import dev.aronba.javelin.components.workspace.Workspace;
import dev.aronba.javelin.util.LastProjectManager;

import javax.swing.*;
import java.awt.*;


public class NavBar extends JMenuBar {
    Javelin javelin;

    JMenu fileMenu;
    JMenu runMenu;
    JMenu gitMenu;

    JMenuItem closeProjectItem;
    JMenuItem openProjectItem;
    JMenuItem newProjectItem;

    public NavBar(Javelin javelin){
        this.javelin = javelin;
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        closeProjectItem = new JMenuItem("close Project");
        openProjectItem = new JMenuItem("open Project");
        newProjectItem = new JMenuItem("new Project");


        closeProjectItem.addActionListener(actionEvent -> closeProject());
        openProjectItem.addActionListener(actionEvent -> openProject());
        newProjectItem.addActionListener(actionEvent -> newProject());

        fileMenu = new JMenu("File");

        fileMenu.add(closeProjectItem);
        fileMenu.add(openProjectItem);
        fileMenu.add(newProjectItem);

        add(fileMenu);

        gitMenu = new JMenu("Git");

        add(gitMenu);

        runMenu = new JMenu("Run");

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("runButton.png"));
        Image image = icon.getImage();
        Image resize = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        runMenu.setIcon(new ImageIcon(resize));
        runMenu.addMouseListener(new CustomMenuMouseListener(this::runProject));

        add(runMenu);






    }

    private void closeProject(){
        LastProjectManager.removeLastProject();
        javelin.setCurrentComponent(new StartMenu(javelin));

    }
    private void runProject(){
        if (javelin.getCurrentJavelinComponent() instanceof Workspace workspace && (workspace.getProject() != null)){

                workspace.getProject().runMain();

        }
    }
    private void openProject(){
        javelin.setCurrentComponent(new Workspace());
    }
    private void newProject(){
        javelin.setCurrentComponent(new Workspace());
    }

}
