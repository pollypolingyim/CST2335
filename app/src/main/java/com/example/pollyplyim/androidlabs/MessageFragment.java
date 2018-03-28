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


    public static MessageFragment newInstance(long id, String message) {
        Bundle args = new Bundle();
        args.putLong("ID", id);
        args.putString("Message", message);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        //getIntent() turns red

        final long id = getArguments().getLong("ID", 0);
        String message = getArguments().getString("Message");

        ((TextView) view.findViewById(R.id.textView2)).setText(String.valueOf(id));
        ((TextView) view.findViewById(R.id.textView)).setText(message);

        deleteButton = (Button) view.findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (getActivity() instanceof MessageDetails) {
                    ((MessageDetails) getActivity()).deleteMessageWithId(id);
                } else {
                    ((ChatWindowActivity) getActivity()).deleteMessageWithId(id);
                }
            }
        });

        return view;
    }
}
