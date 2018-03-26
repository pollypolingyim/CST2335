package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindowActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    private ListView listView;
    private EditText editT;
    private Button sendBut;
    private FrameLayout frame;
    private ArrayList<String> chatMsg;
    private ChatAdapter messageAdapter;
    private ChatDatabaseHelper cdh;
    private static SQLiteDatabase sld;
    private ContentValues cv;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = (ListView)findViewById(R.id.chatList);
        editT = (EditText) findViewById(R.id.chatWindowEditText);
        sendBut = (Button) findViewById(R.id.sendBut);
        frame =(FrameLayout) findViewById(R.id.frameLayout1);
        chatMsg = new ArrayList<String>();
        cdh = new ChatDatabaseHelper(getApplicationContext());
        sld = cdh.getWritableDatabase();
        cv = new ContentValues();

        if(frame!=null)
            Log.i(ACTIVITY_NAME, "frame layout has loaded successfully");
        if(frame==null)
            Log.i(ACTIVITY_NAME, "frame layout did not load");

        String str = "select "+ ChatDatabaseHelper.KEY_ID + ", "+
                ChatDatabaseHelper.KEY_MESSAGE + " from " + ChatDatabaseHelper.TB_NAME;
        cursor = sld.rawQuery(str, null);

        int colIndex = cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE );

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            String s = cursor.getString(colIndex);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " +s);
            chatMsg.add(s);
            cursor.moveToNext();
        }

        int n = cursor.getColumnCount();
        Log.i(ACTIVITY_NAME, "Cursor's column count="+n);
        for (int i=0; i<n; i++)
            Log.i(ACTIVITY_NAME,cursor.getColumnName(colIndex));

        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),MessageDetails.class);
                intent.putExtra("ID",0);
                intent.putExtra("Message", messageAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void sendMsg(View view){
        String msg = editT.getText().toString();
        chatMsg.add(msg);
        cv.put(ChatDatabaseHelper.KEY_MESSAGE, msg);
        sld.insert(ChatDatabaseHelper.TB_NAME, "NullCol", cv);
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
         public long getItemId(int position){
             cursor.moveToPosition(position);
             int colIndex = cursor.getColumnIndex( ChatDatabaseHelper.KEY_ID );
             return cursor.getLong(colIndex);
         }
    }

    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        cdh.close();
        sld.close();
    }
}
