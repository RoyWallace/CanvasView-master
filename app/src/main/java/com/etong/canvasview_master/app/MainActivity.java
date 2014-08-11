package com.etong.canvasview_master.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

    private String [] str = {"CircleCanvasBase","CircleCanvas with ImageView"};

    public MainActivity getThis(){
        return MainActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        ListView listView = getListView();
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str));

<<<<<<< HEAD
            }
        });



        circleCanvas = (CircleCanvasView) findViewById(R.id.view);
        circleCanvas.setMeteor(rb);
        circleCanvas.setStarNumber(5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                a.start();
//                b.start();
            circleCanvas.startMeteorAnim();

            }

        });
=======
>>>>>>> origin/master
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){

            case 0:
                goToActivity(CircleCanvasDemo1.class);
                break;
            case 1:
                goToActivity(MeteorActivity.class);
                break;
        }
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

    public void goToActivity(Class<?> c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

}
