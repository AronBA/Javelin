package dev.aronba.javelin.util;

import java.io.File;
import java.util.prefs.Preferences;

public class LastProjectManager {

    private static final String KEY_LAST_PROJECT = "last_project";
    public static void saveLastProject(File lastProject) {
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        preferences.put(KEY_LAST_PROJECT, lastProject.getAbsolutePath());
    }
    public static File getLastProject(){
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        String lastProjectPath = preferences.get(KEY_LAST_PROJECT, null);

        if (lastProjectPath != null) {
            return new File(lastProjectPath);
        } else {
            return null;
        }
    }
    public static void removeLastProject(){
        Preferences preferences = Preferences.userNodeForPackage(LastProjectManager.class);
        preferences.remove(KEY_LAST_PROJECT);
    }

}
