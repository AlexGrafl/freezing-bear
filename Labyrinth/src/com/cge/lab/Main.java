package com.cge.lab;

import com.jogamp.newt.opengl.GLWindow;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.*;
import java.awt.*;

public class Main {

    private static String TITLE = "CGE - Labyrinth";  // window's title
    private static final int WINDOW_WIDTH = 1024;  // width of the drawable
    private static final int WINDOW_HEIGHT = 768; // height of the drawable

    public static void main(String[] args) {

        // Run the GUI codes in the event-dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Get the default OpenGL profile, reflecting the best for your running platform
                GLProfile glp = GLProfile.getDefault();
                // Specifies a set of OpenGL capabilities, based on your profile.
                GLCapabilities caps = new GLCapabilities(glp);
                // Allocate a GLWindow and add GLEventListener
                GLWindow window = GLWindow.create(caps);
                window.addGLEventListener(new LabyrinthEventListener());
                window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                window.setTitle(TITLE);
                window.setVisible(true);
            }
        });
    }
}
