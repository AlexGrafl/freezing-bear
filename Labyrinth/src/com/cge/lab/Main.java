package com.cge.lab;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.*;

public class Main {

    private static String TITLE = "CGE - Labyrinth";  // window's title
    private static final int WINDOW_WIDTH = 1024;  // width of the drawable
    private static final int WINDOW_HEIGHT = 768; // height of the drawable
    private static final int FPS = 60; // animator's target frames per second

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

                final FPSAnimator animator = new FPSAnimator(window, FPS, true);
                window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDestroyNotify(WindowEvent arg0) {
                        // Use a dedicate thread to run the stop() to ensure that the
                        // animator stops before program exits.
                        new Thread() {
                            @Override
                            public void run() {
                                animator.stop();
                                System.exit(0);
                            }
                        }.start();
                    }
                });
                LabyrinthEventListener labyrinthEventListener = new LabyrinthEventListener();
                window.addGLEventListener(labyrinthEventListener);
                window.addKeyListener(labyrinthEventListener);
                window.addMouseListener(labyrinthEventListener);
                window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                window.setTitle(TITLE);
                window.setVisible(true);
                animator.start();

            }
        });
    }
}
