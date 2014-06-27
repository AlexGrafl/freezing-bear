package com.cge.lab;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static javax.media.opengl.GL2.*;
import static java.awt.event.KeyEvent.*;

import com.cge.lab.LoadMaze.FieldType;

/**
 * @author Alex
 */


/*
    TODO:   - Lighting
            - Floor
            - Collision Detection
            - Load maze via command line parameter oder so
 */


public class LabyrinthEventListener implements GLEventListener, KeyListener, MouseListener {

    private Texture crateTexture;
    private String textureFileName = "res/crate.tga";
    private float textureTop, textureBottom, textureLeft, textureRight;
    private GL2 gl;
    private GLU glu;

    //our maze map
    private LoadMaze maze;

    //player properties
    private float lookAngle;
    private float posX = 0;
    private float posZ = 0;
    private float prevPosX;
    private float prevPosZ;

    private float moveIncrement = 0.1f;

    private float walkBias = 0;
    private float walkBiasAngle = 0;


    public LabyrinthEventListener(LoadMaze maze){
        this.maze = maze;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0);
        gl.glDepthFunc(GL_LESS);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting

        try {
            crateTexture = TextureIO.newTexture(new File(textureFileName), false);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            TextureCoords textureCoords = crateTexture.getImageTexCoords();
            textureTop = textureCoords.top();
            textureBottom = textureCoords.bottom();
            textureLeft = textureCoords.left();
            textureRight = textureCoords.right();

        } catch (IOException e) {
            e.printStackTrace();
        }
        posZ = -maze.getStartX() * 2;
        posX = -maze.getStartY() * 2;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        crateTexture.destroy(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Player at headingY. Rotate the scene by -headingY instead (add 360 to get a
        // positive angle)
        gl.glRotatef(360.0f - lookAngle, 0, 1.0f, 0);

        // Player is at (posX, 0, posZ). Translate the scene to (-posX, 0, -posZ)
        // instead.
        gl.glTranslatef(-posX, -walkBias - 0.25f, -posZ);

        //Enable textures for crates
        crateTexture.enable(gl);
        crateTexture.bind(gl);
        gl.glPushMatrix();
        //draw crates
        for(ArrayList<FieldType> list : maze.getMap()){
            for(FieldType type : list){
                //draw a crate where a crate belongs
                if(type == FieldType.CRATE) drawCube();
                gl.glTranslatef(0f, 0f, -2f);
            }
            //basically a carriage return
            gl.glTranslatef(2f, 0f, 2 * list.size());
        }

        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl = glAutoDrawable.getGL().getGL2();

        if (height == 0) height = 1;   // prevent divide by zero
        float aspect = (float)width / height;
        // Set the view port (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
        gl.glLoadIdentity();             // reset projection matrix
        glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    void drawCube()
    {
        gl.glBegin(GL_QUADS);
        // Front Face
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // bottom-left of the texture and quad
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);  // bottom-right of the texture and quad
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);   // top-right of the texture and quad
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);  // top-left of the texture and quad

        // Back Face
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        // Top Face
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        // Bottom Face
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right face
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Left Face
        gl.glTexCoord2f(textureLeft, textureBottom);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(textureRight, textureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(textureRight, textureTop);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(textureLeft, textureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case VK_W:
                prevPosX = posX;
                prevPosZ = posZ;
                posX -= (float)Math.sin(Math.toRadians(lookAngle)) * moveIncrement;
                posZ -= (float)Math.cos(Math.toRadians(lookAngle)) * moveIncrement;
                walkBiasAngle = (walkBiasAngle >= 359.0f) ? 0.0f : walkBiasAngle + 10.0f;

                // Causes the player to bounce in sine-wave pattern rather than
                // straight-line (head bobbing)
                walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
                break;
            case VK_S:
                prevPosX = posX;
                prevPosZ = posZ;
                posX += (float)Math.sin(Math.toRadians(lookAngle)) * moveIncrement;
                posZ += (float)Math.cos(Math.toRadians(lookAngle)) * moveIncrement;
                walkBiasAngle = (walkBiasAngle <= 1.0f) ? 359.0f : walkBiasAngle - 10.0f;
                walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
                break;

             case VK_D:
                 prevPosX = posX;
                 prevPosZ = posZ;
                 posX -= (float)Math.sin(Math.toRadians(lookAngle - 90)) * moveIncrement;
                 posZ -= (float)Math.cos(Math.toRadians(lookAngle - 90)) * moveIncrement;
                break;
            case VK_A:
                prevPosX = posX;
                prevPosZ = posZ;
                posX -= (float)Math.sin(Math.toRadians(lookAngle + 90)) * moveIncrement;
                posZ -= (float)Math.cos(Math.toRadians(lookAngle + 90)) * moveIncrement;
                break;
        }
        System.out.println("X: " + posX + " Z: "+ posZ);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        lookAngle = -mouseEvent.getX() % 360;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }
}
