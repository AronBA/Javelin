package dev.aronba.javelin.components;

import dev.aronba.javelin.util.FileIO;
import dev.aronba.javelin.Javelin;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class TextEditor extends JPanel implements JavelinComponent {

    public File currentlyEditingFile;
    public RSyntaxTextArea textArea;
    private JScrollPane textAreaScrollPane;
    private JScrollPane fileTreeScrollPane;
    private JTree fileTree;
    private Javelin javelin;

    public TextEditor(Dimension dimension){
        this.setLayout(new BorderLayout());
        setTextArea(dimension);
        setFileTree();

    }
    public TextEditor(Dimension dimension,File file){
        currentlyEditingFile = file;
        setTextArea(dimension,file);
        setFileTree();

    }
    private void setFileTree(){

    }

    private void setTextArea(Dimension dimension, File file) {
        setTextArea(dimension);
        textArea.append(FileIO.read(file));
    }
    private void setTextArea(Dimension dimension){
        textArea = new RSyntaxTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,19));
        textArea.setBackground(new Color(0xFF2B2D2F, true));
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/style.xml"));
            theme.apply(textArea);


        } catch (Exception e){
        e.printStackTrace();
        }

        textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setPreferredSize(sizeFrame(dimension));
        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        TextLineNumber textLineNumber = new TextLineNumber(textArea);
        textLineNumber.setUpdateFont(false);
        textAreaScrollPane.setRowHeaderView(textLineNumber);

        this.add(textAreaScrollPane, BorderLayout.EAST);
    }
    private Dimension sizeFrame(Dimension dimension){

        dimension.setSize(dimension.getWidth() - 50, dimension.getHeight() - 50);
        return dimension;
    }


    @Override
    public void setJavelinReference(Javelin javelin) {
        this.javelin = javelin;
    }

}
