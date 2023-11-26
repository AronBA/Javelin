package dev.aronba.javelin.components.workspace;

import dev.aronba.javelin.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import org.fife.ui.rsyntaxtextarea.FileTypeUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The TextEditor class represents a component in the Javelin IDE for editing text files.
 * It includes features such as syntax highlighting, line numbering, and the ability to open and edit files.
 */
@Getter
@Setter
public class TextEditor extends JSplitPane {

    /**
     * The file currently being edited in the text editor.
     */
    private File currentlyEditingFile;

    /**
     * The main text area for editing the content.
     */
    private RSyntaxTextArea textArea;

    /**
     * The scroll pane containing the text area.
     */
    private JScrollPane textAreaScrollPane;

    /**
     * The label displaying the name of the currently edited file.
     */
    private JLabel fileNameLabel;

    /**
     * Constructs a TextEditor object with no initially opened file.
     */
    public TextEditor() {
        initialize();
    }

    /**
     * Constructs a TextEditor object and opens the specified file for editing.
     *
     * @param file The file to open and edit in the text editor.
     */
    public TextEditor(File file) {
        this.currentlyEditingFile = file;
        initialize();
    }

    /**
     * Initializes the components and layout of the text editor.
     */
    private void initialize() {
        this.setLayout(new BorderLayout());

        String name = (currentlyEditingFile == null) ? "unsaved file" : currentlyEditingFile.getName();
        fileNameLabel = new JLabel(name);

        textArea = new RSyntaxTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setCodeFoldingEnabled(true);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/style.xml"));
            theme.apply(textArea);
        } catch (Exception e) {
            System.out.println("no theme :(");
        }

        textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        TextEditorLineNumber textEditorLineNumber = new TextEditorLineNumber(textArea);
        textEditorLineNumber.setUpdateFont(false);

        textAreaScrollPane.setRowHeaderView(textEditorLineNumber);

        this.add(fileNameLabel, BorderLayout.NORTH);
        this.add(textAreaScrollPane, BorderLayout.CENTER);
    }

    /**
     * Opens the specified file for editing in the text editor.
     *
     * @param file The file to open and edit.
     */
    public void openFile(File file) {
        this.fileNameLabel.setText(file.getName());
        this.textArea.setText(FileUtil.read(file));
        this.textArea.setSyntaxEditingStyle(FileTypeUtil.get().guessContentType(file));
        this.currentlyEditingFile = file;
        revalidate();
        repaint();
    }
}
