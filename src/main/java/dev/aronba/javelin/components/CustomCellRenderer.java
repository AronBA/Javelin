package dev.aronba.javelin.components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class CustomCellRenderer extends DefaultTreeCellRenderer {

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
    private String getFileType(File file){
        String fileName = file.getName();
        int dotI = fileName.lastIndexOf(".");
        if (dotI > 0 && dotI < fileName.length() -1){
            return fileName.substring(dotI+1).toLowerCase();
        } else {
            return "Unknown";
        }
    }


}
