package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by pollyplyim on 2018-03-24.
 */

public class MessageDetails extends Activity {
    FragmentTransaction fragmentTransaction;
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);
        if(isTablet) { // how do I identify if it is a phone or a tablet
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_layout,
                    (MessageFragment) getFragmentManager().findFragmentById(R.id.fragment_layout))
                                .addToBackStack(null)
                                .commit();
        }
        else{
            //what should I put in the first parameter?
            startActivity(new Intent(getContext(),MessageFragment.class), savedInstanceState);
        }
    }




}
