package dev.aronba.javelin;

import dev.aronba.javelin.component.sidebar.SideBar;

import javax.swing.*;
import java.awt.*;

public class Javelin extends JFrame {

    private final SideBar sideBar;
    public Javelin() {

       this.setLayout(new BorderLayout());


        this.sideBar = new SideBar();
        this.add(sideBar,BorderLayout.WEST);


       this.setSize(400,400);
       this.setVisible(true);




  }
}
