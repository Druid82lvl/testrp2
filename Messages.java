package com.example.koste.communicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Messages extends AppCompatActivity {

    ListView listView;
    String  sending_user_id;
    String receiving_user_id;
    BackgroundWorker_Messages bw_m;
    EditText editText_Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent intent = getIntent();

        String result = intent.getStringExtra("result");
        sending_user_id = intent.getStringExtra("sending_user_id");
        receiving_user_id  = intent.getStringExtra("receiving_user_id");
        editText_Message = (EditText) findViewById(R.id.editText_Message);
      // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

        String [] table = result.split("<br>");
        String [] table_to_show = new String[9];

        int a;
        if(table.length > 10){
            a = 9;
            for(int i = 0; i < 9; i++){
                table_to_show[i] = table[table.length - a];
                a--;
            }
        }else{
            table_to_show = table;
        }


        ListAdapter contactAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,table_to_show);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(contactAdapter);
    }

    public void SendMessages(View view){
       // Toast.makeText(this,"insert_messages"  + " "+  sending_user_id + " "+ receiving_user_id+ " "+ editText_Message.getText().toString(),Toast.LENGTH_SHORT).show();
      bw_m = new BackgroundWorker_Messages(this);
      bw_m.execute("insert_messages", sending_user_id, receiving_user_id, editText_Message.getText().toString(),"nor");
      //  finish();
    }

    public void RefreshMessages(String result, String sending_user_id, String receiving_user_id){

        String [] table = result.split("<br>");
        String [] table_to_show = new String[9];

        int a;
        if(table.length > 10){
            a = 9;
            for(int i = 0; i < 9; i++){
                table_to_show[i] = table[table.length - a];
                a--;
            }
        }else{
            table_to_show = table;
        }

        ListAdapter contactAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,table_to_show);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(contactAdapter);

    }
}
