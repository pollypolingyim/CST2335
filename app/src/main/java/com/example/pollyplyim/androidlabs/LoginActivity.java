package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    SharedPreferences sharedpreferences;
    public static final String mypref = "mypref";
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = (EditText) findViewById(R.id.editText0);
        sharedpreferences = getSharedPreferences(mypref, MODE_PRIVATE);
        editEmail.setText(sharedpreferences.getString("emailKey", "email@domain.com"));
    }

    public void saveEmail(View view) {
        String e = editEmail.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("emailKey", e);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);

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
