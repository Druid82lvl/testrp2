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
 * Created by koste on 15.10.2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void, String>{

    String login; //
    String password; //
    Context ctx; //

    BackgroundWorker(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
      /* If params[0] equals login or
      register app connecting with HTTP protocol to get available contacts or approval of adding an user*/
      String type = params[0]; //"login" or "register"

        if(type.equals("login")){
             login = params[1];
             password = params[2];
            try {
                URL url = new URL("http://10.0.2.2/communicator/return_contacts.php");
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();

                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);

                OutputStream ops = huc.getOutputStream();

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));

                String post_data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login,"UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("register")){
            login = params[1];
            password = params[2];
            try {
                URL url = new URL("http://10.0.2.2/communicator/register.php");
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();

                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);

                OutputStream ops = huc.getOutputStream();

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));

                String post_data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login,"UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        /*If result equals something else than (" Error , Wrong password or login, User added , User already exists "),
        that means user is logged and
         in result are info about (id and login of user) and (id's and logins all of available contacts) */
        if((s.equals("Error")) || (s.equals("Wrong password or login")) || (s.equals("User added"))
                || (s.equals("User already exists"))){
            Toast.makeText(ctx,s,Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(ctx,Contacts.class);
            intent.putExtra("result",s);
            ctx.startActivity(intent);
        }
    }


}
