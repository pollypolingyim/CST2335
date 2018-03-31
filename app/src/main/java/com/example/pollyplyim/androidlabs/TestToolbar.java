package com.example.pollyplyim.androidlabs;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TestToolbar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
    }

    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch(id){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                //Start an activity…
                break;
            case R.id.action_three:
                break;
            case R.id.action_four:
                Toast.makeText(getApplicationContext(),
                        "Version 1.0, by Polly Yim", Toast.LENGTH_LONG).show();
        }
        return true;

    }



}
