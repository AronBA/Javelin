package dev.aronba.javelin.component.sidebar;

import dev.aronba.javelin.component.Component;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

public class SideBarComponent  extends  JPanel{

    private final String name;

    private final ImageIcon icon;
    private String shortCut;
    private Component component;
    private String position;
    private JButton button;
    private boolean isOpen;

    SideBarComponent(String name, ImageIcon icon){
        this.name = name;
        this.icon = icon;

        this.add(new JButton("test"));

    }

    void handleClick(){
        if (isOpen){
            this.isOpen = false;
            component.close();
        } else {
            component.open();
            this.isOpen = true;
        }
    }

}
