package dev.aronba.javelin.components.workspace;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

import static dev.aronba.javelin.util.FileUtil.getFileType;

/**
 * The FileTreeCellRenderer class customizes the rendering of cells in the JTree representing the file tree.
 * It sets the text and icon for each cell based on the associated File object.
 */
public class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     * Overrides the default rendering of tree cells to customize text and icon based on File properties.
     *
     * @param tree      The JTree being rendered.
     * @param value     The value of the cell to be rendered.
     * @param sel       True if the cell is selected.
     * @param expanded  True if the cell is expanded.
     * @param leaf      True if the cell is a leaf (has no children).
     * @param row       The row index of the cell being rendered.
     * @param hasFocus  True if the cell has focus.
     * @return The component used for rendering the cell.
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        // Customize cell text and icon based on the associated File object
        if (value instanceof DefaultMutableTreeNode node && node.getUserObject() instanceof File file) {
            setText(file.getName());
            try {
                // Attempt to load an icon based on the file type
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(getFileType(file) + ".png"));
                Image image = icon.getImage();
                Image resize = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(resize));
            } catch (Exception e) {
                // Handle exception if icon is not found
                e.printStackTrace();
            }
        }

        return this;
    }
}
