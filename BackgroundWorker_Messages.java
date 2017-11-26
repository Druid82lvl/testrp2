package com.example.koste.communicator;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by koste on 29.10.2017.
 */

public class BackgroundWorker_Messages extends AsyncTask<String,Void,String>{

    Context ctx;
    String receiving_user_name;
    String sending_user_id;
    String receiving_user_id;
    String type;
    String content;

    Messages messages;

    BackgroundWorker_Messages(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params){

        type = params[0];
       if(type.equals("download_messages")) {

           //Downloading Messages

           sending_user_id = params[1];
           receiving_user_id = params[2];

          if(params[3].equals("refresh")){
              doRefresh = true;
           }


           try {
                URL url = new URL("http://10.0.2.2/communicator/return_messages.php");

                HttpURLConnection huc = (HttpURLConnection) url.openConnection();

                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);

                OutputStream ops = huc.getOutputStream();

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));

                String post_data = URLEncoder.encode("sending_user_id", "UTF-8") + "=" + URLEncoder.encode(sending_user_id,"UTF-8")
                        + "&" +
                        URLEncoder.encode("receiving_user_id", "UTF-8") + "=" + URLEncoder.encode(receiving_user_id,"UTF-8");

                bw.write(post_data);
                bw.flush();
                bw.close();
                ops.close();

                InputStream ins = huc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));

                String result = "";
                String line;

                while((line = br.readLine()) != null){
                    result = result + line;
                }
                br.close();
                ins.close();
                huc.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(type.equals("insert_messages")){
           //Inserting Messages


               sending_user_id = params[1];
               receiving_user_id = params[2];
               content = params[3];

               try {
                   URL url = new URL("http://10.0.2.2/communicator/insert_messages.php");

                   HttpURLConnection huc = (HttpURLConnection) url.openConnection();

                   huc.setRequestMethod("POST");
                   huc.setDoOutput(true);
                   huc.setDoInput(true);

                   OutputStream ops = huc.getOutputStream();

                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));

                   String post_data = URLEncoder.encode("sending_user_id", "UTF-8") + "=" + URLEncoder.encode(sending_user_id,"UTF-8")
                           + "&" +
                           URLEncoder.encode("receiving_user_id", "UTF-8") + "=" + URLEncoder.encode(receiving_user_id,"UTF-8")
                           + "&" +
                           URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content,"UTF-8");

                   bw.write(post_data);
                   bw.flush();
                   bw.close();
                   ops.close();

                   InputStream ins = huc.getInputStream();
                   BufferedReader br = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));

                   String result = "";
                   String line;

                   while((line = br.readLine()) != null){
                       result = result + line;
                   }
                   br.close();
                   ins.close();
                   huc.disconnect();

                   return result;

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               } catch (ProtocolException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
        return null;
    }

   boolean doRefresh = false; //
    @Override
    protected void onPostExecute(String s){
        //If
        if(s.equals("Error")  || (s.equals("Message Sent"))){
            if(s.equals("Message Sent")){
                BackgroundWorker_Messages bwm = new BackgroundWorker_Messages(ctx);
                doRefresh = true;
                bwm.execute("download_messages",sending_user_id,receiving_user_id,"refresh");
            }
            Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
        }else{
            if(doRefresh){
                  messages = new Messages();
                  messages.RefreshMessages(s,sending_user_id,receiving_user_id);
            }else{
                Intent intent = new Intent(ctx, Messages.class);
                intent.putExtra("result",s);
                intent.putExtra("receiving_user_id",receiving_user_id);
                intent.putExtra("sending_user_id",sending_user_id);

                ctx.startActivity(intent);
            }

        }
    }
}
