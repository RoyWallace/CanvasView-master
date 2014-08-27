package com.etong.canvasview_master.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class MeteorActivity extends ActionBarActivity implements View.OnClickListener {

    private CircleCanvasView circleCanvas;

    private ImageView rb;

    private ImageView imageView;

    private ImageButton imageButton;

    private ImageButton imageButton2;

    AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteor);

        findAllView();
        
        setupAnim();

        setAlllistener();
    }

    private void findAllView() {

        rb = (ImageView) findViewById(R.id.imageView2);

        imageView = (ImageView) findViewById(R.id.imageView);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        circleCanvas = (CircleCanvasView) findViewById(R.id.view);
        circleCanvas.setMeteor(rb);
//        circleCanvas.setKnockPoint(200,100);
//        circleCanvas.setStarNumber(2);
    }

    private void setupAnim() {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(rb, "translationX", -100f);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(rb, "translationY", 100f);

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(xAnimator,yAnimator);

    }

    private void setAlllistener(){
        rb.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(view == rb){
            Log.i("etong","click");
            circleCanvas.startMeteorAnim();
        }
    }
}
