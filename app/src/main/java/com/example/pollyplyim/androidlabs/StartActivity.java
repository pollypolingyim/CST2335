package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button = (Button)findViewById(R.id.button);
    }

    public void goToListItemsActivity(View view) {
        Intent intent = new Intent(this, ListItemsActivity.class);
        startActivityForResult(intent, 50);
    }

    public void startChat (View view){
        Log.i(ACTIVITY_NAME, "User click start chat!");
        Intent i = new Intent(this, ChatWindowActivity.class);
        startActivity(i);
    }

    public void weatherForecast (View view){
        Log.i(ACTIVITY_NAME, "User clicks weather forecast");
        Intent i = new Intent(this, WeatherForecast.class);
        startActivity(i);
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode==50) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if(responseCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Log.i(ACTIVITY_NAME, messagePassed);
            Toast.makeText(getApplicationContext(),
                    "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume (){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
