package dev.aronba.javelin.util;

import java.awt.*;
import java.util.prefs.Preferences;
public class LastComponentSizeManager {
    private static final Preferences preferences = Preferences.userNodeForPackage(LastComponentSizeManager.class);
    private LastComponentSizeManager(){}
    public static void saveComponentSizes(Container container) {
        Component[] components = container.getComponents();

        for (Component component : components) {
            String componentName = component.getName();
            if (componentName != null) {
                Dimension size = component.getSize();
                preferences.putInt(componentName + "_width", size.width);
                preferences.putInt(componentName + "_height", size.height);
            }
        }
    }
    public static Dimension getSavedSize(Component component) {
        String componentName = component.getName();
        int width = preferences.getInt(componentName + "_width", -1);
        int height = preferences.getInt(componentName + "_height", -1);

        if (width != -1 && height != -1) {
            return new Dimension(width, height);
        } else {
            return null;
        }
    }

}
