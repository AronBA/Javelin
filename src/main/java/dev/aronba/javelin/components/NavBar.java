package dev.aronba.javelin.components;




import dev.aronba.javelin.Javelin;
import dev.aronba.javelin.components.workspace.Workspace;
import dev.aronba.javelin.util.LastProjectManager;

import javax.swing.*;


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

        add(runMenu);


    }

    private void closeProject(){
        LastProjectManager.removeLastProject();
        javelin.setCurrentComponent(new StartMenu(javelin));

    }
    private void openProject(){
        javelin.setCurrentComponent(new Workspace());
    }
    private void newProject(){
        javelin.setCurrentComponent(new Workspace());
    }

}
