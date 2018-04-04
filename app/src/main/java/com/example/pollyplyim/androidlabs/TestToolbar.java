package com.example.pollyplyim.androidlabs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TestToolbar extends AppCompatActivity {
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Version 1.0, by Polly Yim", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                SharedPreferences sharedPref = getSharedPreferences("newMessage", Context.MODE_PRIVATE);
                String str = sharedPref.getString("newMessage", "You selected item 1");
                Snackbar.make(fab, str, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                break;
            case R.id.action_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.toolbar_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_three:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_layout, null);
                final EditText edt =  v.findViewById(R.id.snackbar_msg) ;
                builder2.setView(v)
                        // Add action buttons
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences sharedPref = getSharedPreferences("newMessage", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sharedPref.edit();
                                edit.putString("newMessage", edt.getText().toString());
                                edit.apply();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder2.show();

                break;
            case R.id.action_four:
                Toast.makeText(getApplicationContext(),
                        "Version 1.0, by Polly Yim", Toast.LENGTH_LONG).show();
        }
        return true;

    }



}
