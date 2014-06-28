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
    TODO:   - Lighting (incl. lights attached to roof)
            - Load maze via command line parameter oder so
            - Walls
 */


public class LabyrinthEventListener implements GLEventListener, KeyListener, MouseListener {

    private Texture crateTexture, floorTexture;
    private String crateTextureFileName = "res/crate.tga", floorTextureFileName = "res/lava_glow.tga";
    private float crateTextureTop, crateTextureBottom, crateTextureLeft, crateTextureRight, floorTextureTop,
            floorTextureBottom, floorTextureLeft, floorTextureRight;
    private GL2 gl;
    private GLU glu;

    //our maze map
    private LoadMaze maze;

    //player properties
    private float lookAngleX = 0, lookAngleY = 0;
    private float posX = 0;
    private float posZ = 0;
    private float prevPosX;
    private float prevPosZ;

    private float moveIncrement = 0.1f;

    private float walkBias = 0;
    private float walkBiasAngle = 0;

    private boolean up = true;
    private float upValue = 0.25f;


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
            //set up crate texture
            crateTexture = TextureIO.newTexture(new File(crateTextureFileName), false);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            crateTexture.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            TextureCoords textureCoords = crateTexture.getImageTexCoords();
            crateTextureTop = textureCoords.top();
            crateTextureBottom = textureCoords.bottom();
            crateTextureLeft = textureCoords.left();
            crateTextureRight = textureCoords.right();

            //setup floor texture
            floorTexture = TextureIO.newTexture(new File(floorTextureFileName), false);
            floorTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
            floorTexture.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
            floorTexture.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            floorTexture.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            textureCoords = floorTexture.getImageTexCoords();
            floorTextureTop = textureCoords.top();
            floorTextureBottom = textureCoords.bottom();
            floorTextureLeft = textureCoords.left();
            floorTextureRight = textureCoords.right();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prevPosZ = posZ = -maze.getStartX() * 2;
        prevPosX = posX = -maze.getStartY() * 2;
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

        checkCollision();
        // Player at lookAngle. Rotate the scene by -lookAngle instead (add 360 to get a
        // positive angle) for X and Y directions
        gl.glRotatef(360f - lookAngleY, 1.0f, 0, 0f);
        gl.glRotatef(360.0f - lookAngleX, 0, 1.0f, 0);

        // Player is at (posX, 0, posZ). Translate the scene to (-posX, 0, -posZ)
        // instead. upValue is a "debug"-y-offset
        gl.glTranslatef(-posX, -walkBias - upValue, -posZ);
        floorTexture.enable(gl);
        floorTexture.bind(gl);
        drawFloor();

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

    private void checkCollision() {
        float posX = this.posX, posZ = this.posZ;
        //calculate indices from current position
        int indexX = (int)(Math.abs(posX) + 1) / 2, indexY = (int) (Math.abs(posZ) + 1) / 2;
        if(maze.getMap().get(indexX).get(indexY) == FieldType.CRATE){
            //if the field the the current position is a crate, reset the position to the previous one
            this.posX = prevPosX;
            this.posZ = prevPosZ;
            System.out.println("Collision - ix: "+ indexX + " iy: "+ indexY);
        }

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
        glu.gluPerspective(45.0, aspect, 0.01, 100.0); // fovy, aspect, zNear, zFar
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    void drawFloor(){

        //draw the floor as large as the map
        gl.glBegin(GL_QUADS);
        gl.glTexCoord2f(floorTextureRight, floorTextureTop);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(floorTextureLeft, floorTextureTop);
        gl.glVertex3f(maze.getMap().size() * 2, -1.0f, -1.0f);
        gl.glTexCoord2f(floorTextureLeft, floorTextureBottom);
        gl.glVertex3f(maze.getMap().size() * 2, -1.0f, - maze.getMap().get(0).size() * 2);
        gl.glTexCoord2f(floorTextureRight, floorTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, - maze.getMap().get(0).size() * 2);

        gl.glEnd();
    }

    void drawCube()
    {
        gl.glBegin(GL_QUADS);
        // Front Face
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // bottom-left of the texture and quad
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);  // bottom-right of the texture and quad
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);   // top-right of the texture and quad
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);  // top-left of the texture and quad

        // Back Face
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        // Top Face
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        // Bottom Face
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right face
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Left Face
        gl.glTexCoord2f(crateTextureLeft, crateTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureBottom);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureRight, crateTextureTop);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(crateTextureLeft, crateTextureTop);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case VK_W:
                //set previous position
                prevPosX = posX;
                prevPosZ = posZ;
                //calculate next position, depending on the direction you are facing
                posX -= (float)Math.sin(Math.toRadians(lookAngleX)) * moveIncrement;
                posZ -= (float)Math.cos(Math.toRadians(lookAngleX)) * moveIncrement;

                //this is used to get the head bobbing
                walkBiasAngle = (walkBiasAngle >= 359.0f) ? 0.0f : walkBiasAngle + 10.0f;

                // Causes the player to bounce in sine-wave pattern rather than
                // straight-line (head bobbing)
                walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
                break;
            case VK_S:
                prevPosX = posX;
                prevPosZ = posZ;
                posX += (float)Math.sin(Math.toRadians(lookAngleX)) * moveIncrement;
                posZ += (float)Math.cos(Math.toRadians(lookAngleX)) * moveIncrement;
                walkBiasAngle = (walkBiasAngle <= 1.0f) ? 359.0f : walkBiasAngle - 10.0f;
                walkBias = (float)Math.sin(Math.toRadians(walkBiasAngle)) / 20.0f;
                break;

             case VK_D:
                 prevPosX = posX;
                 prevPosZ = posZ;
                 //Strafe right (means, calculate next position depending on lookAngle - 90 degrees
                 posX -= (float)Math.sin(Math.toRadians(lookAngleX - 90)) * moveIncrement;
                 posZ -= (float)Math.cos(Math.toRadians(lookAngleX - 90)) * moveIncrement;
                break;
            case VK_A:
                prevPosX = posX;
                prevPosZ = posZ;
                //Strafe left
                posX -= (float)Math.sin(Math.toRadians(lookAngleX + 90)) * moveIncrement;
                posZ -= (float)Math.cos(Math.toRadians(lookAngleX + 90)) * moveIncrement;
                break;
            case VK_B:
                //toggle up or down
                up = !up;
                break;
            case VK_SPACE:
                //move camera up/down
                if(up){
                    upValue += 0.5;
                }else{
                    upValue -= 0.5;
                }
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
        //get the current looking angles
        lookAngleX = -mouseEvent.getX() % 360;
        lookAngleY = -mouseEvent.getY() % 360;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }
}
