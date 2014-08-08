package com.etong.canvasview_master.app;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class MainActivity extends ActionBarActivity {

    private CircleCanvasView circleCanvas;

    private Button btn;

    private ImageView rb;

    private ImageView imageView;

    private ImageButton imageButton;

    private ImageButton imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();
    }

    private void findAllView() {

        rb = (ImageView) findViewById(R.id.imageView2);

        imageView = (ImageView) findViewById(R.id.imageView);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        btn= (Button) findViewById(R.id.button);

        final ObjectAnimator a = ObjectAnimator.ofFloat(rb, "translationY", 0, 50,95,135,170,200,225);
        final ObjectAnimator b = ObjectAnimator.ofFloat(rb, "translationX", 0, 0,-5,-40,-90,-150,-225);

        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        circleCanvas = (CircleCanvasView) findViewById(R.id.view);
        circleCanvas.setMeteor(rb);
//        circleCanvas.setKnockPoint(200,100);
//        circleCanvas.setStarNumber(2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                a.start();
//                b.start();
            circleCanvas.startMeteorAnim();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
