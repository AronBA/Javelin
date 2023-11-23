package dev.aronba.javelin.components;

import dev.aronba.javelin.util.FileIO;
import dev.aronba.javelin.Javelin;
import dev.aronba.javelin.util.LastProjectManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;


public class TextEditor extends JPanel implements JavelinComponent {

    public File projectRoot;
    public boolean hasChanges;
    public File currentlyEditingFile;
    public RSyntaxTextArea textArea;
    private JScrollPane textAreaScrollPane;
    private JScrollPane fileTreeScrollPane;
    private JTree fileTree;
    private JLabel header;
    private Javelin javelin;

    private Color color = new Color(0xFF1F2228, true);

    public TextEditor(Dimension dimension) {
        this.setLayout(new BorderLayout());
        setTextArea(dimension);
        setFileTree();
        setPreferredSize(sizeFrame(dimension));

    }

    public TextEditor(Dimension dimension, File file) {
        this.setLayout(new BorderLayout());
        if (file.isFile()) currentlyEditingFile = file;
        if (file.isDirectory()) projectRoot = file;
        LastProjectManager.saveLastProject(file);

        setTextArea(dimension, file);
        setFileTree();

        setPreferredSize(sizeFrame(dimension));
    }

    private void setFileTree() {
        fileTree = new JTree(generateFileTree());
        fileTree.setCellRenderer(new CustomCellRenderer());
        fileTree.addTreeSelectionListener(tsl -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tsl.getPath().getLastPathComponent();

            File file = (File) node.getUserObject();
            openFile(file);
        });

        fileTreeScrollPane = new JScrollPane(fileTree);
        fileTreeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fileTreeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(fileTreeScrollPane, BorderLayout.WEST);
    }
    private void openFile(File file) {
        if (file.isFile()){
            this.header.setText( file.getName());
            this.textArea.setText(FileIO.read(file));
            this.textArea.setEditable(true);
            this.textArea.setFont(new Font("Arial",Font.BOLD,14));
            this.currentlyEditingFile = file;
        } else if (file.isDirectory()){
            this.header.setText("");
            this.textArea.setText("Nothing here");
            this.textArea.setEditable(false);
            this.textArea.setFont(new Font("Arial",Font.BOLD,50));
        }

        repaint();
        revalidate();
    }
    private DefaultTreeModel generateFileTree() {

        String path = (projectRoot == null) ? currentlyEditingFile.getParentFile().getAbsolutePath() : projectRoot.getAbsolutePath();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
        loadFiles(new File(path), root);

        return new DefaultTreeModel(root);
    }
    private void loadFiles(File dir, DefaultMutableTreeNode parentNode) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file);

                    loadFiles(file, childNode);
                    parentNode.add(childNode);
                } else {
                    DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(file);
                    parentNode.add(leafNode);
                }
            }
        }
    }
    private void setTextArea(Dimension dimension, File file) {
        setTextArea(dimension);
        textArea.setText(FileIO.read(file));
    }
    private void setTextArea(Dimension dimension) {

        String filename = (currentlyEditingFile == null) ? "unsaved file" : currentlyEditingFile.getName();
        header = new JLabel(filename);
        header.setBackground(color);

        textArea = new RSyntaxTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 19));
        textArea.setBackground(color);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/style.xml"));
            theme.apply(textArea);


        } catch (Exception e) {
            e.printStackTrace();
        }

        textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder());
        TextLineNumber textLineNumber = new TextLineNumber(textArea);
        textLineNumber.setUpdateFont(false);
        textAreaScrollPane.setRowHeaderView(textLineNumber);


        JPanel fileArea = new JPanel();
        fileArea.setLayout(new BorderLayout());
        fileArea.add(header, BorderLayout.NORTH);
        fileArea.add(textAreaScrollPane, BorderLayout.CENTER);
        this.add(fileArea, BorderLayout.CENTER);
    }
    private Dimension sizeFrame(Dimension dimension) {

        dimension.setSize(dimension.getWidth() - 50, dimension.getHeight() - 50);
        return dimension;
    }
    @Override
    public void setJavelinReference(Javelin javelin) {
        this.javelin = javelin;
    }

}
