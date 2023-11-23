package dev.aronba.javelin.components;

import dev.aronba.javelin.Javelin;

import javax.swing.*;

public class Settings extends JPanel implements JavelinComponent {

    private Javelin javelin;

    Settings(){

    }

    @Override
    public void setJavelinReference(Javelin javelin) {
        this.javelin = javelin;
    }
}
