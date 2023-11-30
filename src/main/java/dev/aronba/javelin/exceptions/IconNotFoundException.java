package dev.aronba.javelin.exceptions;

import java.io.File;

public class IconNotFoundException extends Exception{
    public IconNotFoundException(File file) {
        super("Icon not found for file: " + file.getName());
    }
}
