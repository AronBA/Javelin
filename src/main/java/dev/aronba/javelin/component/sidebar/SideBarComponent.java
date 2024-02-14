package dev.aronba.javelin.component.sidebar;

import dev.aronba.javelin.component.Component;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequiredArgsConstructor
public abstract class SideBarComponent implements Component{

    private final String name;
    private final ImageIcon icon;
    private String shortCut;
    private String position;
    private JButton button;

    private boolean isOpen;

    void handleClick(){
        if (isOpen){
            this.isOpen = false;
            close();
        } else {
            open();
            this.isOpen = true;
        }
    }

}
