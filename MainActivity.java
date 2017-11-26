package com.example.koste.communicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BackgroundWorker bw;
    EditText et_Login, et_Password;
    String login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view){
        // on Button Login click
        bw = new BackgroundWorker(this);
        et_Login = (EditText) findViewById(R.id.et_Login);
        et_Password = (EditText) findViewById(R.id.et_Password);

        String login = et_Login.getText().toString();
        String password = et_Password.getText().toString();
        bw.execute("login",login,password);
    }

    public void onRegister(View view){
        // on Button Register click
        bw = new BackgroundWorker(this);
        et_Login = (EditText) findViewById(R.id.et_Login);
        et_Password = (EditText) findViewById(R.id.et_Password);

        String login = et_Login.getText().toString();
        String password = et_Password.getText().toString();
        bw.execute("register",login,password);
    }
}
