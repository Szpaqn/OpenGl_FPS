package com.jakubmichalowski.opengl_fps;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *  OpenGL Custom renderer used with GLSurfaceView 
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
   Context context;   // Application's context

   private float pi;

   private float xpos;
   private float ypos;
   private float zpos;

   public float getXrot() {
      return xrot;
   }

   public void addToXrot(float dx) {
      this.xrot += dx;
   }

   public float getYrot() {
      return yrot;
   }

   public void addToYrot(float dy) {
      this.yrot += dy;
   }

   private float xrot;
   private float yrot;

   private float camSpeed = 0.1f;

   public void goForward(){
       float xrotrad, yrotrad;
//       float xposT = xpos;
//       float yposT = ypos;
       float zposT = zpos;
       yrotrad = (yrot / 180 * pi);
       xrotrad = (xrot / 180 * pi);
       xpos += sin(yrotrad) * camSpeed ;
       zpos -= cos(yrotrad) * camSpeed;
       ypos -= sin(xrotrad) * camSpeed;
       if(checkIfColliding(xpos, ypos, zpos)) {
//           xpos = xposT;
//           ypos = yposT;
           zpos = zposT;
       }
   }

   public void goBack(){
       float xrotrad, yrotrad;
//       float xposT = xpos;
//       float yposT = ypos;
       float zposT = zpos;
       yrotrad = (yrot / 180 * pi);
       xrotrad = (xrot / 180 * pi);
       xpos -= sin(yrotrad) * camSpeed;
       zpos += cos(yrotrad) * camSpeed;
       ypos += sin(xrotrad) * camSpeed;
       if(checkIfColliding(xpos, ypos, zpos)) {
//           xpos = xposT;
//           ypos = yposT;
           zpos = zposT;
       }
   }

   public void strafeLeft() {
       float yrotrad;
       float xposT = xpos;
//       float zposT = zpos;
       yrotrad = (yrot / 180 * pi);
       xpos -= cos(yrotrad) * camSpeed;
       zpos -= sin(yrotrad) * camSpeed;
       if(checkIfColliding(xpos, ypos, zpos)) {
           xpos = xposT;
//           zpos = zposT;
       }
   }

   public void strafeRight() {
       float yrotrad;
       float xposT = xpos;
//       float zposT = zpos;
       yrotrad = (yrot / 180 * pi);
       xpos += cos(yrotrad) * camSpeed;
       zpos += sin(yrotrad) * camSpeed;
       if(checkIfColliding(xpos, ypos, zpos)) {
           xpos = xposT;
//           zpos = zposT;
       }
   }



   private Pyramid pyramid;

//   private Cube cube;
   private TextureCube cube;


   // Constructor with global application context
   public MyGLRenderer(Context context) {

      this.context = context;

      pi = 3.141592654f;
//      triangle = new Triangle();
      pyramid = new Pyramid();
//      square = new Square();
      cube = new TextureCube();
//      cube2 = new Cube2();
//      cube = new Cube();

      xpos = 0;
      ypos = 0;
      zpos = 5;
      xrot = 0;
      yrot = 0;


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
  
//       You OpenGL|ES initialization code here
      cube.loadTexture(gl, context);    // Load image into Texture
      gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
//
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

//      gl.glFrustumf(x, upDown, 0f, 0, 0, 0);
      // Use perspective projection
      GLU.gluPerspective(gl, 60, aspect, 0.1f, 100.f);
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
      gl.glLoadIdentity();    //Reset model-view matrix


//      GLU.gluLookAt(gl, x, 0, z, 0, 0, 0, 0,1,0);  //TODO: sprawdzić http://nehe.gamedev.net/article/camera_class_tutorial/18010/ oraz https://www.opengl.org/discussion_boards/showthread.php/178047-about-gluLookAt-function-and-how-to-rotate-the-camera
//      GLU.gluPerspective(gl, 45, x, 0.1f, 100.f);


      gl.glRotatef(xrot, 1.0f, 0, 0);
      gl.glRotatef(yrot, 0, 1.0f, 0);
      gl.glTranslatef(-xpos,-ypos, -zpos);
      cube.draw(gl);

      gl.glLoadIdentity();                 // Reset the mode-view matrix


      gl.glRotatef(xrot, 1.0f, 0, 0);
      gl.glRotatef(yrot, 0, 1.0f, 0);
      gl.glTranslatef(-xpos+3,-ypos, -zpos); // Translate left and into the screen
      pyramid.draw(gl);                              // Draw the pyramid

      Log.d("MyGL", "onDrawFrame: xrot: " + String.valueOf(xrot) + " yrot: " + String.valueOf(yrot)  + " xpos: " + String.valueOf(xpos) + " ypos: " + String.valueOf(ypos) + " zpos: " + String.valueOf(zpos));

   }

   private boolean checkIfColliding(float xpos, float ypos, float zpos) {
      return ((zpos < 1.4f && zpos > -1.4f) && (xpos < 1.4f && xpos > -1.4f) && (ypos < 1.4f && ypos > -1.4f ));
   }
}