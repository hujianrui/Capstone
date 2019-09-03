package com.capstone.sportsmate;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.button_Login);
        Button buttonRegistered = findViewById(R.id.button_Registered);

        buttonLogin.setOnClickListener(this);
        buttonRegistered.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_Login: {


            }

            case R.id.button_Registered: {
                Intent messageIntent = new Intent(this, RegisterActivity.class);
                this.startActivity(messageIntent);
            }

        }
    }
}
