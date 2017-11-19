package com.jakubmichalowski.opengltriangles;

import android.support.v7.app.AppCompatActivity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
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
        this.previousX = 0;
        this.previousY = 0;
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



        String log = "onTouchEvent: X = " + String.valueOf(x) + " Y =" + y;
        Log.d("FPS", log );
        if(x < glView.getWidth()/2){
            if(y < glView.getHeight()/2){
                glRenderer.setZ((float)glRenderer.getZ() + (float)0.1);
            } else {
                glRenderer.setZ((float)glRenderer.getZ() + (float)-0.1);
            }
        } else {
            float dx = previousX - x;
            float dy = previousY - y;

            if(dx < 0){ //w prawo
                glRenderer.setX(glRenderer.getX() + (float)0.1);
            } else { // w lewo
                glRenderer.setX(glRenderer.getX() - (float)0.1);
            }

            if(dy < 0){ //w górę
                glRenderer.setY(glRenderer.getY() + (float)0.1);
            } else { // w dół
                glRenderer.setY(glRenderer.getY() - (float)0.1);
            }

        }


        previousY = y;
        previousX = x;
        return true;
    }
}
