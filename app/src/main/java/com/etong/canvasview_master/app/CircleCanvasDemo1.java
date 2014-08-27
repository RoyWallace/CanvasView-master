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

    private Button btn,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,backBtn;

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
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        backBtn = (Button) findViewById(R.id.back_btn);

    }

    private void setListener(){
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        backBtn.setOnClickListener(this);
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
            circleCanvasLayout.ZoomIn();
        }else if(view ==btn2){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.LEFT_TOP);
        }else if(view ==btn3){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.LEFT_CENTER);
        }else if(view ==btn4){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.LEFT_BOTTOM);
        }else if(view ==btn5){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.CENTER_TOP);
        }else if(view ==btn6){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.CENTER);
        }else if(view ==btn7){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.CENTER_BOTTOM);
        }else if(view ==btn8){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.RIGHT_TOP);
        }else if(view ==btn9){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.RIGHT_CENTER);
        }else if(view ==btn10){
            circleCanvasLayout.setCirclePosition(CircleCanvasLayout.RIGHT_BOTTOM);
        }else if(view ==backBtn){
            circleCanvasLayout.ZoomOut();
        }
    }
}
