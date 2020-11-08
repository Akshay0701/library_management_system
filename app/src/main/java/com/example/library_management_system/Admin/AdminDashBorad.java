package com.example.library_management_system.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.library_management_system.R;

public class AdminDashBorad extends AppCompatActivity {

    CardView add_Books,view_Books,update_Books,remove_Books,request_issue_Books,request_new_Books,view_issued_Books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_borad);

        add_Books=findViewById(R.id.add_Books);
        view_Books=findViewById(R.id.view_Books);
        update_Books=findViewById(R.id.update_Books);
        remove_Books=findViewById(R.id.remove_Books);
        request_issue_Books=findViewById(R.id.request_issue_Books);
        request_new_Books=findViewById(R.id.request_new_Books);
        view_issued_Books=findViewById(R.id.view_issued_Books);

        add_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Add_Books.class));
            }
        });
        view_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,View_Books.class));
            }
        });
        update_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Update_Books.class));
            }
        });
        remove_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Remove_Books.class));
            }
        });
        request_issue_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Request_Issue_Books.class));
            }
        });
        request_new_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Request_New_Books.class));
            }
        });
        view_issued_Books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBorad.this,Issued_Books.class));
            }
        });
    }
}