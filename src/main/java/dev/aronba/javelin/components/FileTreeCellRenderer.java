package dev.aronba.javelin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

import static dev.aronba.javelin.util.FileIO.getFileType;

/**
 * The FileTreeCellRenderer class customizes the rendering of cells in the JTree representing the file tree.
 * It sets the text and icon for each cell based on the associated File object.
 */
public class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final Logger logger = LoggerFactory.getLogger(Workspace.class);

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode node && node.getUserObject() instanceof File file) {
            setText(file.getName());
            try {
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(getFileType(file) + ".png"));
                Image image = icon.getImage();
                Image resize = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(resize));
            } catch (Exception e) {

               //todo -> stupid ass log logger.warn("Icon not found for File: "  + file.getName());

            }
        }

        return this;
    }
}
