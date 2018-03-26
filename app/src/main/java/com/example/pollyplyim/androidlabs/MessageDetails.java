package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by pollyplyim on 2018-03-24.
 */

public class MessageDetails extends Activity {
    private FragmentTransaction fragmentTransaction;


    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_layout,
                    (MessageFragment) getFragmentManager().findFragmentById(R.id.fragment_layout))
                                .addToBackStack(null)
                                .commit();
        }
        else{
            startActivity(new Intent(this, MessageDetails.class), savedInstanceState);
        }
    }
}
