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

/**
 * The Javelin class represents the main application window for the Javelin IDE.
 * It extends JFrame and manages the user interface components such as the navigation bar,
 * the current Javelin component, and handles the initialization of the application.
 */
public class Javelin extends JFrame {

    /**
     * The navigation bar component for Javelin.
     */
    private final NavBar navBar;

    /**
     * The currently active Javelin component.
     */
    @Getter
    private Component currentJavelinComponent;

    /**
     * Constructs a Javelin object, initializes the main application window, sets up the user interface,
     * and determines whether to show the start menu or load the last project.
     */
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
            SwingUtilities.invokeLater(() -> {
                Project project = new Project(lastProjectRoot);
                setCurrentComponent(new Workspace(project));
                setFullScreen();
            });
        }

        this.setVisible(true);
    }

    /**
     * Sets the main application window to full screen mode.
     */
    public void setFullScreen() {
        this.setSize(JavelinSize.NORMAL);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.repaint();
        this.revalidate();
    }

    /**
     * Sets the current Javelin component and updates the main application window accordingly.
     *
     * @param newComponent The new Javelin component to set.
     */
    public void setCurrentComponent(Component newComponent) {
        if (this.currentJavelinComponent != null) this.remove(currentJavelinComponent);

        this.navBar.setVisible(!(newComponent instanceof StartMenu));
        this.add(newComponent, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        this.currentJavelinComponent = newComponent;
    }
}
