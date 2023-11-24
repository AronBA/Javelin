package dev.aronba.javelin.components;

import dev.aronba.javelin.util.CompilHelper;
import dev.aronba.javelin.util.FileIO;
import dev.aronba.javelin.Javelin;
import dev.aronba.javelin.exceptions.CompilationFailedException;
import dev.aronba.javelin.util.LastProjectManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Optional;

public class MenuBar extends JMenuBar {

    JMenu fileMenu;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem openItem;
    JMenuItem closeItem;
    JMenu componentMenu;
    JMenuItem startItem;
    JMenuItem editorItem;
    JMenuItem consoleItem;
    JMenu runMenu;
    JMenuItem runItem;

    Javelin javelin;
    Component currentComponent;


    public MenuBar(Javelin javelin) {
        this.javelin = javelin;
        this.currentComponent = javelin.getCurrentJavelinComponent();

        fileMenu = new JMenu("File");

        openItem = new JMenuItem("Open");
        exitItem = new JMenuItem("Exit");
        saveItem = new JMenuItem("Save");
        closeItem = new JMenuItem("Close Project");

        openItem.addActionListener(this::open);
        exitItem.addActionListener(this::exit);
        saveItem.addActionListener(this::save);
        closeItem.addActionListener(this::close);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fileMenu.add(closeItem);

        this.add(fileMenu);

        componentMenu = new JMenu("Components");

        editorItem = new JMenuItem("Editor");
        startItem = new JMenuItem("Start");
        consoleItem = new JMenuItem("Console");

        startItem.addActionListener(action -> javelin.setCurrentComponent(new StartMenu()));
        editorItem.addActionListener(action -> javelin.setCurrentComponent(new TextEditor(javelin.getSize())));
        consoleItem.addActionListener(action -> javelin.setCurrentComponent(new Console()));

        componentMenu.add(startItem);
        componentMenu.add(editorItem);
        componentMenu.add(consoleItem);

        this.add(componentMenu);

        runMenu = new JMenu("Run");

        runItem = new JMenuItem("Java");
        runItem.addActionListener(this::run);

        runMenu.add(runItem);


        this.add(runMenu);


    }

    private void run(ActionEvent actionEvent) {

        File file = FileIO.openFile().get();


        if (CompilHelper.compileJava(file) == 0) {
            Process process = CompilHelper.runJava(file.getName());

            javelin.setCurrentComponent(new Console(process));


        } else throw new CompilationFailedException();

    }
    private void close(ActionEvent actionEvent){
        if (currentComponent instanceof TextEditor){
            StartMenu startMenu = new StartMenu();
            startMenu.setJavelinReference(javelin);
            LastProjectManager.removeLastProject();
            javelin.setSize(new Dimension( 400,400));
            javelin.setCurrentComponent(startMenu);

        }
    }
    private void save(ActionEvent actionEvent) {
        if (!(currentComponent instanceof TextEditor textEditor)) {
            System.out.printf("nothing to save");
            return;
        }
        System.out.println("saved something");

        FileIO.saveFile(textEditor.currentlyEditingFile, textEditor.textArea.getText());
    }

    private void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void open(ActionEvent actionEvent) {
        if (!(currentComponent instanceof TextEditor textEditor)) {
            System.out.printf("nothing to open a file in");
            return;
        }

        Optional<File> file = FileIO.openFile();

        if (file.isPresent()) {
            String text = FileIO.read(file.get());

            textEditor.textArea.setText(text);
        }


    }


    public void setCurrentComponent(Component currentComponent) {
        this.currentComponent = currentComponent;
    }
}
