package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindowActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    ListView listView;
    EditText editT;
    Button sendBut;
    ArrayList<String> chatMsg;
    ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = (ListView)findViewById(R.id.chatList);
        editT = (EditText) findViewById(R.id.chatWindowEditText);
        sendBut = (Button) findViewById(R.id.sendBut);
        chatMsg = new ArrayList<String>();

        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
    }

    public void sendMsg(View view){
        String msg = editT.getText().toString();
        chatMsg.add(msg);
        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
        editT.setText("");
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
         public int getCount(){
            return chatMsg.size();
        }

         public String getItem(int position){
             return chatMsg.get(position);
         }

         public View getView(int position, View convertView, ViewGroup parent){
             LayoutInflater inflater = ChatWindowActivity.this.getLayoutInflater();
             View result = null ;
             if(position%2 == 0)
                 result = inflater.inflate(R.layout.chat_row_incoming, null);
             else
                 result = inflater.inflate(R.layout.chat_row_outgoing, null);
             TextView message = (TextView)result.findViewById(R.id.message_text);
             message.setText(getItem(position)); // get the string at position
             return result;
         }

         public long getId(int position){
             return position;
         }

    }
}
