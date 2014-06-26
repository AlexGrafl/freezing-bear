package com.cge.lab;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.io.File;
import java.io.IOException;

import static javax.media.opengl.GL4.*;

/**
 * @author Alex
 */
public class LabyrinthEventListener implements GLEventListener {
    private Texture crate;
    private GL4 gl;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL4();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0);
        gl.glDepthFunc(GL_LESS);
        gl.glEnable(GL_DEPTH_TEST);
       // gl.glShadeModel(GL_SMOOTH);
        try {
            crate = TextureIO.newTexture(new File("res/crate.tga"), true);
            crate.bind(gl);
            crate.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
            crate.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
            crate.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            crate.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL4();
        crate.destroy(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL4();

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl = glAutoDrawable.getGL().getGL4();
        if(height == 0) height = 1;
        float aspect = (float)width / height;
        gl.glViewport(0, 0, width, height);
        gl
    }
}
