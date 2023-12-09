package dev.aronba.javelin.components;

import javax.swing.*;

public class RenderedTextPane extends JEditorPane {



    RenderedTextPane(String renderedMd){

        this.setContentType("text/html");
        this.setText(renderedMd);
        this.setEditable(false);

    }




}
