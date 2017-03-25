package com.example.rose.zoo.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rose.zoo.DatabaseOperations;
import com.example.rose.zoo.R;

/**
 * Created by Rose on 01/09/2017.
 */

public class RegisterActivity extends Activity {
    EditText USER_NAME, USER_PASS, CON_PASS;
    String user_name, user_pass, con_pass;
    Button REGISTER;
    Context CTX = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        USER_NAME = (EditText) findViewById(R.id.reg_user);
        USER_PASS = (EditText) findViewById(R.id.reg_pass);
        CON_PASS =(EditText) findViewById(R.id.reg_pass_confirm);
        REGISTER = (Button) findViewById(R.id.register_button);
        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = USER_NAME.getText().toString();
                user_pass = USER_PASS.getText().toString();
                con_pass = CON_PASS.getText().toString();

                if(!(user_pass.equals((con_pass)))){
                    Toast.makeText(getBaseContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                    USER_NAME.setText("");
                    USER_PASS.setText("");
                    CON_PASS.setText("");
                }
                else{
                    DatabaseOperations DB = new DatabaseOperations(CTX);
                    DB.putInformation(DB, user_name, user_pass);
                    Toast.makeText(getBaseContext(), "Registration success", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }
}
