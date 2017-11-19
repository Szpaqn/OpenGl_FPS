package com.jakubmichalowski.opengltriangles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
/**
 *  OpenGL Custom renderer used with GLSurfaceView 
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
   Context context;   // Application's context

   public float getZ() {
      return z;
   }

   public void setZ(float z) {
      this.z = z;
   }

   private float z;

   public float getX() {
      return x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return y;
   }

   public void setY(float y) {
      this.y = y;
   }

   private float x;
   private float y;

//   Triangle triangle;
//   private Pyramid pyramid;
//   Square square;
   private TextureCube cube;

   // Rotational angle and speed
//   private float angleTriangle = 0.0f;
//   private float angleQuad = 0.0f;
//   private float speedTriangle = 0.5f;
//   private float speedQuad = -0.4f;

   private static float anglePyramid = 0; // Rotational angle in degree for pyramid
   private static float angleCube = 0;    // Rotational angle in degree for cube
   private static float speedPyramid = 2.0f; // Rotational speed for pyramid
//   private static float speedCube = -1.5f;   // Rotational speed for cube

   // Constructor with global application context
   public MyGLRenderer(Context context) {

      this.context = context;

//      triangle = new Triangle();
//      pyramid = new Pyramid();
//      square = new Square();
      cube = new TextureCube();
//      cube2 = new Cube2();
      z = -6;
      x = 0;
      y = 0;

   }

   
   // Call back when the surface is first created or re-created
   @Override
   public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
      gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
      gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
      gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
      gl.glEnable(GL10.GL_BLEND);
      gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
      gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
      gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
  
      // You OpenGL|ES initialization code here
      cube.loadTexture(gl, context);    // Load image into Texture
      gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture

      }
   
   // Call back after onSurfaceCreated() or whenever the window's size changes
   @Override
   public void onSurfaceChanged(GL10 gl, int width, int height) {
      if (height == 0) height = 1;   // To prevent divide by zero
      float aspect = (float)width / height;
   
      // Set the viewport (display area) to cover the entire window
      gl.glViewport(0, 0, width, height);
  
      // Setup perspective projection, with aspect ratio matches viewport
      gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
      gl.glLoadIdentity();                 // Reset projection matrix
      // Use perspective projection
      GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);
      gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
      gl.glLoadIdentity();                 // Reset
  
      // You OpenGL|ES display re-sizing code here
      // ......
   }
   
   // Call back to draw the current frame.
   @Override
   public void onDrawFrame(GL10 gl) {
      // Clear color and depth buffers using clear-value set earlier
      gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

      // You OpenGL|ES rendering code here
//      gl.glLoadIdentity();    //Reset model-view matrix
//      gl.glTranslatef(0, 0.0f, -6.0f); // Translate left and into the screen
////      gl.glRotatef(angleTriangle, 0.0f, 1.0f, 0.0f); // Rotate the triangle about the y-axis
////      triangle.draw(gl);                   // Draw triangle
//      gl.glRotatef(anglePyramid, 0.1f, 1f, -0.1f); // Rotate
//      pyramid.draw(gl);                              // Draw the pyramid


      // Translate right, relative to the previous translation
      gl.glLoadIdentity();                 // Reset the mode-view matrix

//      gl.glTranslatef(0.0f, 0.0f, 0);  // Translate right and into the screen
      GLU.gluLookAt(gl, 0, 0, z, 0, 0, 0, 0, 1, 0);
//      gl.glRotatef(angleCube, 0.4f, 0.9f, -0.1f);
//      gl.glRotatef(angleQuad, 1.0f, 0.0f, 0.0f); // Rotate the square about the x-axis
//      square.draw(gl);                       // Draw
      gl.glRotatef(angleCube, 0.4f, 0.9f, -0.1f);
      cube.draw(gl);



//      cube2.draw();

      // Update the rotational angle after each refresh
//      angleTriangle += speedTriangle;
//      anglePyramid += speedPyramid;
//      angleQuad += speedQuad;
//      angleCube += speedCube;
      angleCube += speedPyramid;

   }
}