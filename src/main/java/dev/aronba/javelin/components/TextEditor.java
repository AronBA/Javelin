package dev.aronba.javelin.components;

import dev.aronba.javelin.util.FileIO;
import lombok.Getter;
import lombok.Setter;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.FileTypeUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The TextEditor class represents a component in the Javelin IDE for editing text files.
 * It includes features such as syntax highlighting, line numbering, and the ability to open and edit files.
 */
@Getter
@Setter
public class TextEditor extends JSplitPane {


    private static final Logger logger = LoggerFactory.getLogger(TextEditor.class);

    private File currentlyEditingFile;
    private RSyntaxTextArea textArea;
    private RenderedTextPane renderedTextPane;
    private RTextScrollPane textAreaScrollPane;
    private JScrollPane renderedTextPaneScrollPane;
    private JLabel fileNameLabel;
    private JSplitPane textEditorRenderedEditorSplit;

    public TextEditor() {
        initialize();
    }

    public TextEditor(File file) {
        this.currentlyEditingFile = file;
        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        String name = (currentlyEditingFile == null) ? "unsaved file" : currentlyEditingFile.getName();
        fileNameLabel = new JLabel(name);

        textArea = new RSyntaxTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setCodeFoldingEnabled(true);

        textAreaScrollPane = new RTextScrollPane(textArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String path = "/style.xml";
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(path));
            theme.apply(textArea);

        } catch (IOException e) {
            //todo-> stupid ass log logger.warn("could not load theme from" + path);
        }



        CompletionProvider provider = createCompletionProvider();
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);

        this.add(fileNameLabel, BorderLayout.NORTH);
        this.add(textAreaScrollPane, BorderLayout.CENTER);

    }

    private CompletionProvider createCompletionProvider() {

        DefaultCompletionProvider provider = new DefaultCompletionProvider();


        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));

        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        provider.addCompletion(new ShorthandCompletion(provider, "sout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "soue",
                "System.err.println(", "System.err.println("));

        return provider;
    }

    public void openFile(File file) {
        SwingUtilities.invokeLater(() -> {
            if (FileIO.getFileType(file).equalsIgnoreCase("md")) {
                openRenderedMarkDownRenderedTextPane(file);
            } else {
                this.remove(textEditorRenderedEditorSplit);
                this.add(textAreaScrollPane, BorderLayout.CENTER);
            }

            this.fileNameLabel.setText(file.getName());
            this.textArea.setText(FileIO.read(file));
            this.textArea.setSyntaxEditingStyle(FileTypeUtil.get().guessContentType(file));
            this.currentlyEditingFile = file;
        });
    }

    private void openRenderedMarkDownRenderedTextPane(File file) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(FileIO.read(file));
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        String renderedMD = renderer.render(document);

        SwingUtilities.invokeLater(() -> {

            renderedTextPane = new RenderedTextPane(renderedMD);
            renderedTextPaneScrollPane = new JScrollPane(renderedTextPane);

            renderedTextPaneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            renderedTextPaneScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            textEditorRenderedEditorSplit = new JSplitPane(HORIZONTAL_SPLIT, textAreaScrollPane, renderedTextPaneScrollPane);

            this.add(textEditorRenderedEditorSplit, BorderLayout.CENTER);
        });


    }
}
