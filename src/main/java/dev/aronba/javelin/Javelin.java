package dev.aronba.javelin;

import dev.aronba.javelin.components.NavBar;
import dev.aronba.javelin.components.Project;
import dev.aronba.javelin.components.StartMenu;
import dev.aronba.javelin.components.Workspace;
import dev.aronba.javelin.util.LastProjectManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The Javelin class represents the main application window for the Javelin IDE.
 * It extends JFrame and manages the user interface components such as the navigation bar,
 * the current Javelin component, and handles the initialization of the application.
 */
public class Javelin extends JFrame {
    public static final Dimension DIMENSION_NORMAL = new Dimension(1000, 1000);
    public static final Dimension DIMENSION_SMALL = new Dimension(400, 400);

    @Getter
    private Component currentJavelinComponent;
    private static final Logger logger = LoggerFactory.getLogger(Javelin.class);
    private static final String TITLE = "Javelin";
    private final NavBar navBar;

    public Javelin() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.loadIcon();
        this.navBar = new NavBar(this);
        this.setJMenuBar(navBar);
        this.loadLastProject();
        this.setVisible(true);
    }

    public void setFullScreen() {
        SwingUtilities.invokeLater(() -> {
            this.setSize(DIMENSION_NORMAL);
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.repaint();
            this.revalidate();
        });
    }

    public void setCurrentComponent(Component newComponent) {
        SwingUtilities.invokeLater(() -> {
            if (this.currentJavelinComponent != null) this.remove(currentJavelinComponent);
            this.navBar.setVisible(!(newComponent instanceof StartMenu));
            this.add(newComponent, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            this.currentJavelinComponent = newComponent;
        });
    }

    private void loadIcon() {
        try {
            this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/logo.png")).getImage());
        } catch (NullPointerException e) {
            logger.warn("No Icon for the JFrame was found");
        }
    }

    private void loadLastProject() {
        File lastProjectRoot = LastProjectManager.getLastProject();

        if (lastProjectRoot == null) {
            StartMenu startMenu = new StartMenu(this);
            setCurrentComponent(startMenu);
            logger.info("No last Project found");
            return;
        }

        Project project = new Project(lastProjectRoot);
        setFullScreen();
        setCurrentComponent(new Workspace(project));
        logger.info("Last project found: " + lastProjectRoot.getPath());
    }
}
