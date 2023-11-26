package dev.aronba.javelin;

import dev.aronba.javelin.components.NavBar;
import dev.aronba.javelin.components.StartMenu;
import dev.aronba.javelin.components.workspace.Workspace;
import dev.aronba.javelin.util.JavelinSize;
import dev.aronba.javelin.util.LastProjectManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

public class Javelin extends JFrame {

    private final NavBar navBar;
    @Getter
    private Component currentJavelinComponent;

    public Javelin() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Javelin");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());
        this.navBar = new NavBar(this);
        this.setJMenuBar(navBar);

        File lastProjectRoot = LastProjectManager.getLastProject();

        if (lastProjectRoot == null) {
            StartMenu startMenu = new StartMenu(this);
            setCurrentComponent(startMenu);

        } else {
            SwingUtilities.invokeLater(() ->{
                Project project = new Project(lastProjectRoot);
                setCurrentComponent(new Workspace(project));
                setFullScreen();
            });
        }

        this.setVisible(true);
    }

    public void setFullScreen() {
            this.setSize(JavelinSize.NORMAL);
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.repaint();
            this.revalidate();
    }

    public void setCurrentComponent(Component newComponent) {
            if (this.currentJavelinComponent != null) this.remove(currentJavelinComponent);

            this.navBar.setVisible(!(newComponent instanceof StartMenu));
            this.add(newComponent,BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            this.currentJavelinComponent = newComponent;

    }
}
