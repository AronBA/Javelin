package dev.aronba.javelin.util;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * The LastProjectManager class provides utility methods for saving, retrieving,
 * and removing the last opened project's file path using Java Preferences.
 */
public class LastProjectManager {

    /**
     * The key used to store the last opened project's file path in the preferences.
     */
    private static final String KEY_LAST_PROJECT = "last_project";

    /**
     * Saves the file path of the last opened project to the preferences.
     *
     * @param lastProject The File representing the last opened project.
     */
    public static void saveLastProject(File lastProject) {
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        preferences.put(KEY_LAST_PROJECT, lastProject.getAbsolutePath());
    }

    /**
     * Retrieves the file path of the last opened project from the preferences.
     *
     * @return The File representing the last opened project, or null if not found.
     */
    public static File getLastProject() {
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        String lastProjectPath = preferences.get(KEY_LAST_PROJECT, null);

        if (lastProjectPath != null) {
            return new File(lastProjectPath);
        } else {
            return null;
        }
    }

    /**
     * Removes the last opened project's file path from the preferences.
     */
    public static void removeLastProject() {
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        preferences.remove(KEY_LAST_PROJECT);
    }

}
