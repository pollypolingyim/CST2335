package com.example.pollyplyim.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pollyplyim on 2018-03-24.
 */

public class MessageFragment extends Fragment {
    private TextView ID;
    private TextView msg;
    private Button deleteButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        //getIntent() turns red
        ID = ((TextView) view.findViewById(R.id.textView2)).setText(getIntent().getExtras().getString("ID"));
        msg = ((TextView)view.findViewById(R.id.textView)).setText(getIntent().getExtras().getString("Message"));
        deleteButton = (Button)view.findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //did I implement it correctly?
                int resultCode =40;
                Intent intent = new Intent(view.getContext(),ChatWindowActivity.class);
                intent.putExtra("ID",0);
                getActivity().setResult(resultCode, intent);
            }
        });

        return view;
    }
}
