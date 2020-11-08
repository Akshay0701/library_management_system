package com.example.library_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.library_management_system.Admin.AdminDashBorad;
import com.example.library_management_system.Admin.adminLogin;
import com.example.library_management_system.User.UserLogin;
import com.example.library_management_system.User.userRegister;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        findViewById(R.id.admin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, adminLogin.class));
            }
        });
        findViewById(R.id.user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, userRegister.class));
            }
        });
    }
}