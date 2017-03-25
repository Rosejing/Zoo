package com.example.rose.zoo.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rose.zoo.DatabaseOperations;
import com.example.rose.zoo.R;

/**
 * Created by Rose on 01/11/2017.
 */

public class LoginActivity extends AppCompatActivity {
    Button Login, Register;
    EditText USERNAME, USERPASS;
    String username, userpass;
    Context CTX = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Login  = (Button) findViewById(R.id.login_button);
        Register = (Button) findViewById(R.id.register_button);
        USERNAME = (EditText) findViewById(R.id.user_name);
        USERPASS = (EditText) findViewById(R.id.user_pass);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                int status = b.getInt("status");
                if(status == 1){
                    Toast.makeText(getBaseContext(), "Please wait...", Toast.LENGTH_LONG);
                    username = USERNAME.getText().toString();
                    userpass = USERPASS.getText().toString();
                    DatabaseOperations dop = new DatabaseOperations(CTX);
                    Cursor cr = dop.getInformation(dop);
                    cr.moveToFirst();
                    boolean login_status = false;
                    String NAME = "";
                    do{
                        if (username.equals(cr.getString(0)) && (userpass.equals(cr.getString(2)))){
                            login_status = true;
                            NAME= cr.getString(0);
                        }

                    }while(cr.moveToNext());
                    if(login_status){
                        Toast.makeText(getBaseContext(), "login success\n Welcome"+ NAME, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
