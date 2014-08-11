package com.etong.canvasview_master.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CircleCanvasDemo1 extends Activity implements View.OnClickListener{


    private CircleCanvasLayout circleCanvasLayout;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_canvas_demo1);
        
        findAllView();
        setListener();
    }

    private void findAllView() {
        circleCanvasLayout = (CircleCanvasLayout) findViewById(R.id.view);
        btn = (Button) findViewById(R.id.button);
    }

    private void setListener(){
        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.circle_canvas_demo1, menu);
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
        if(view == btn){
            circleCanvasLayout.boom();
        }
    }
}
