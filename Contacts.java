package com.example.koste.communicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Contacts extends AppCompatActivity {

    ListView listView; //listView used to show contacts
    BackgroundWorker_Messages bw_m;
    String sending_user_id, receiving_user_id; // sending = logged receiving = chosen


    String[] table_contacts; //
    String[] table_id_contacts;
    String[] table_name_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        //Extract from result of sending_user_id
        final String sending_user_id = result.substring(result.indexOf("<Log>") + 5, result.lastIndexOf("<Log>"));

        //Separating (id and login logged user) and (id and login available users)
        String contacts = result.substring(result.lastIndexOf("<Log>") + 5);
        table_contacts = contacts.split("<br>"); /*table that contains  $id's + "/" + $login's */
        table_id_contacts = new String[table_contacts.length]; //table of id's
        table_name_contacts = new String[table_contacts.length]; // table on name of contacts



        //Separating id's and logins of available users
        String[] contact = new String[2];
        for(int i=0; i < table_contacts.length; i++){
            contact = table_contacts[i].split("/");
            for(int a=0; a < 2; a++){
                if(a == 0){
                    table_id_contacts[i] = contact[0];
                }else{
                    table_name_contacts[i] = contact[1];
                }
            }
        }


        ListAdapter contactAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,table_name_contacts);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(contactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //After click on particular contacts executes new AsyncTask
                bw_m = new BackgroundWorker_Messages(getApplicationContext());
                bw_m.execute("download_messages",sending_user_id,table_id_contacts[position], table_name_contacts[position],"not_refresh");
            }
        });
    }

}
