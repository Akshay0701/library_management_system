package com.example.library_management_system.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.library_management_system.R;
import com.example.library_management_system.User.UserLogin;
import com.google.firebase.auth.FirebaseAuth;

public class adminLogin extends AppCompatActivity {

    Button admin_loginBtn;
    TextView admin_login_password,admin_login_userName;

    LinearLayout linearLayout;
    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_login_password=findViewById(R.id.admin_login_password);
        admin_login_userName=findViewById(R.id.admin_login_userName);
        admin_loginBtn=findViewById(R.id.admin_loginBtn);
        loading=findViewById(R.id.loading);
        linearLayout=findViewById(R.id.linearLayout);

        loading.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        admin_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign
                String userName,password;
                userName=admin_login_userName.getText().toString();
                password=admin_login_password.getText().toString();
                if(!userName.equals("")||!password.equals("")) {
                    if (userName.equals("Admin") && password.equals("Admin")) {
                        SharedPreferences.Editor editor;
                        editor = PreferenceManager.getDefaultSharedPreferences(adminLogin.this).edit();
                        editor.putString("admin_username", userName.trim());
                        editor.putString("admin_password", password.trim());
                        editor.apply();
                        startActivity(new Intent(adminLogin.this, AdminDashBorad.class));
                    }
                }else{
                    Toast.makeText(adminLogin.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username=prefs.getString("admin_username","");
        String pass=prefs.getString("admin_password","");

        if(username.equals("")&&pass.equals("")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(adminLogin.this,AdminDashBorad.class));
        }
    }
}