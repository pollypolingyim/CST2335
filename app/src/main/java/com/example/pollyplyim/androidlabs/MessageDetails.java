package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by pollyplyim on 2018-03-24.
 */

public class MessageDetails extends Activity {
    private FragmentTransaction fragmentTransaction;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);

        long id = getIntent().getLongExtra("ID", 0);
        String message = getIntent().getStringExtra("Message");

//        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

//        if(!isTablet) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, MessageFragment.newInstance(id, message))
                .commit();
//        }
//        else{
//            startActivity(new Intent(this, MessageDetails.class), savedInstanceState);
//        }
    }

    public void deleteMessageWithId(long id) {
        //did I implement it correctly?
        Intent data = new Intent();
        data.putExtra("ID", id);
        setResult(40, data);
        finish();
    }
}
