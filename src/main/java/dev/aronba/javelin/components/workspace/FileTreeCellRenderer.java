package dev.aronba.javelin.components.workspace;

import dev.aronba.javelin.exceptions.IconNotFoundException;
import dev.aronba.javelin.util.FileUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

import static dev.aronba.javelin.util.FileUtil.getFileType;

public class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode node && node.getUserObject() instanceof File file) {
            setText(file.getName());
                try{

                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(getFileType(file)+".png"));
                Image image = icon.getImage();
                Image resize = image.getScaledInstance(20,20,Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(resize));

                } catch (Exception e){

                }
        }

        return this;
    }



}
