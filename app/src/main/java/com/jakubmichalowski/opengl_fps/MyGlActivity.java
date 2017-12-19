package com.jakubmichalowski.opengl_fps;

import android.support.v7.app.AppCompatActivity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class MyGlActivity extends AppCompatActivity {

    private GLSurfaceView glView;   // Use GLSurfaceView
    private MyGLRenderer glRenderer;
    private float previousX;
    private float previousY;

    // Call back when the activity is started, to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glRenderer = new MyGLRenderer(this);
        glView = new GLSurfaceView(this);           // Allocate a GLSurfaceView
        glView.setRenderer(glRenderer); // Use a custom renderer
        this.setContentView(glView);                // This activity sets to GLSurfaceView
        this.previousX = -1;
        this.previousY = -1;
    }

    // Call back when the activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        float x = e.getX();
        float y = e.getY();

        if(previousX == -1){
            previousX = x;
        }
        if(previousY == -1){
            previousY = y;
        }
        float dx = previousX - x;
        float dy = previousY - y;

//        dx = dx/10;
//        dy = dy/10;

        String log = "onTouchEvent: X = " + String.valueOf(x) + " Y =" + String.valueOf(y) + " glView.getWidth()/2 = " + glView.getWidth()/2 + " glView.getHeight()/2 = " + glView.getHeight()/2;
//        Log.d("FPS", log );
        if(x < (glView.getWidth()/2)){
            if(y < (glView.getHeight()/2)){
//                Log.d("FPS", "if(y < (glView.getHeight()/2 - glView.getHeight()/10))" );
//                glRenderer.setZ(glRenderer.getZ() + 0.1f);
                glRenderer.goForward();
            } else if(y > (glView.getHeight()/2 + 2*(glView.getHeight()/10))){
//                Log.d("FPS", "if(x > (glView.getHeight()/2 + glView.getHeight()/10)" );
//                glRenderer.setZ(glRenderer.getZ() -0.1f);
                glRenderer.goBack();
            } else if(y >= (glView.getHeight()/2) && y <= (glView.getHeight()/2 + 2*(glView.getHeight()/10))){
                if(dx < 0){ //w prawo
//                    glRenderer.setX(glRenderer.getX() + 0.1f);
//                    Log.d("FPS", "if(dx < 0)" );
                    glRenderer.strafeLeft();
                } else { // w lewo
//                    glRenderer.setX(glRenderer.getX() - 0.1f);
//                    Log.d("FPS", "if(dx >= 0)" );
                    glRenderer.strafeRight();
                }
            }
        } else {
            if (dx == 0) {

            } else if (dx < 0) { //w prawo
//                glRenderer.setLeftRight(glRenderer.getLeftRight() + 0.1f);
//                Log.d("FPS", "if(dx < 0)");
                glRenderer.addToYrot(-1f);
            } else { // w lewo
//                glRenderer.setLeftRight(glRenderer.getLeftRight() - 0.1f);
//                Log.d("FPS", "if(dx >= 0)");
                glRenderer.addToYrot(1f);
            }
            if (dy == 0) {

            } else if (dy < 0) { //w górę
//                glRenderer.setUpDown(glRenderer.getUpDown() + (float)0.2);
                glRenderer.addToXrot(-1f);
//                Log.d("FPS", "if(dy < 0)");
            } else { // w dół
//                glRenderer.setUpDown(glRenderer.getUpDown() + (float)-0.2);
                glRenderer.addToXrot(1f);
//                Log.d("FPS", "if(dy >= 0)");
            }
        }

        previousY = y;
        previousX = x;
        return true;
    }
}
