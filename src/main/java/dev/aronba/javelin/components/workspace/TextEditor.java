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

@Getter
@Setter
public class TextEditor extends JSplitPane   {

    private File currentlyEditingFile;
    private RSyntaxTextArea textArea;
    private JScrollPane textAreaScrollPane;
    private JLabel fileNameLabel;


    public TextEditor(){
        initialize();
    }
    public TextEditor(File file){
       this.currentlyEditingFile = file;
       initialize();
    }
    private void initialize(){

        this.setLayout(new BorderLayout());

        String name  = (currentlyEditingFile == null) ? "unsaved file" : currentlyEditingFile.getName();
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
        textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        TextEditorLineNumber textEditorLineNumber = new TextEditorLineNumber(textArea);
        textEditorLineNumber.setUpdateFont(false);

        textAreaScrollPane.setRowHeaderView(textEditorLineNumber);

        this.add(fileNameLabel, BorderLayout.NORTH);
        this.add(textAreaScrollPane, BorderLayout.CENTER);

    }


    public void openFile(File file){

        this.fileNameLabel.setText(file.getName());
        this.textArea.setText(FileUtil.read(file));
        this.textArea.setSyntaxEditingStyle(FileTypeUtil.get().guessContentType(file));
        this.currentlyEditingFile = file;

        revalidate();
        repaint();
    }

}
