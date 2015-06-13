package com.example.dpt.rotateanimdemos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.dpt.rotateanimdemos.widget.LeLoadingView;
import com.example.dpt.rotateanimdemos.widget.SimpleLeLoadingView;
import com.example.dpt.rotateanimdemos.widget.SimpleLeLoadingViewHardware;


public class MainActivity extends Activity {

    private SimpleLeLoadingViewHardware slv1;
    private SimpleLeLoadingView slv2;
    private LeLoadingView slv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slv1=(SimpleLeLoadingViewHardware)findViewById(R.id.slv1);
        slv2=(SimpleLeLoadingView)findViewById(R.id.slv2);
        slv3=(LeLoadingView)findViewById(R.id.slv3);
    }

    public void playAnim(View v){
        int id = v.getId();
        if(id ==R.id.btn1) {
            slv1.testRotateAnimStart();
        }else if(id == R.id.btn2){
            slv2.testRotateAnimStart();
        }else {
            slv3.testRotateAnimStart();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
