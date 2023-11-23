package dev.aronba.javelin;

import dev.aronba.javelin.components.JavelinComponent;
import dev.aronba.javelin.components.MenuBar;
import dev.aronba.javelin.components.StartMenu;
import dev.aronba.javelin.components.TextEditor;
import dev.aronba.javelin.exceptions.ComponentNotJavelinException;
import dev.aronba.javelin.util.LastProjectManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;

public class Javelin extends JFrame {

    private final dev.aronba.javelin.components.MenuBar menuBar;
    @Getter
    private Component currentJavelinComponent;

    public Javelin() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Javelin");

        this.setSize(400, 400);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());

        this.menuBar = new MenuBar(this);
        this.setJMenuBar(menuBar);

        if (LastProjectManager.getLastProject() == null) {
            StartMenu startMenu = new StartMenu();
            startMenu.setJavelinReference(this);
            setCurrentComponent(startMenu);

        } else {
            SwingUtilities.invokeLater(() ->{

                setFullScreen();
                setCurrentComponent(new TextEditor(this.getSize(), LastProjectManager.getLastProject()));


            });
        }

        this.setVisible(true);
    }

    public void setFullScreen() {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.repaint();
            this.revalidate();

    }

    public void setCurrentComponent(Component newComponent) {
            if (!(newComponent instanceof JavelinComponent)) throw new ComponentNotJavelinException();
            if (this.currentJavelinComponent != null) this.remove(currentJavelinComponent);

            this.menuBar.setVisible(!(newComponent instanceof StartMenu));
            this.add(newComponent);
            this.menuBar.setCurrentComponent(newComponent);
            this.revalidate();
            this.repaint();

            this.currentJavelinComponent = newComponent;




    }

}
