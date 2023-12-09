package dev.aronba;

import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import dev.aronba.javelin.Javelin;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        FlatAtomOneDarkIJTheme.setup();
        SwingUtilities.invokeLater(Javelin::new);
    }
}
