package com.example.library_management_system.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.R;
import com.example.library_management_system.User.userRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RequestBook extends Fragment {

    TextView user_request_book,user_request_author;
    Button user_requestBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
        }else{
            startActivity(new Intent(getContext(),userRegister.class));
           getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request_book, container, false);

        user_requestBtn=view.findViewById(R.id.user_requestBtn);
        user_request_book=view.findViewById(R.id.user_request_book);
        user_request_author=view.findViewById(R.id.user_request_author);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Requests");

        user_requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName,authorName;
                bookName=user_request_book.getText().toString();
                authorName=user_request_author.getText().toString();
                if(bookName.isEmpty()){
                    user_request_book.setError("Name is empty");
                    user_request_book.setFocusable(true);
                }
                else if(authorName.isEmpty()){
                    user_request_author.setError("Name is empty");
                    user_request_author.setFocusable(true);
                }else{
                    Toast.makeText(getContext(), "Request Sended To Admin", Toast.LENGTH_SHORT).show();
                    final HashMap<Object,String> hashMap=new HashMap<>();
                    hashMap.put("Name",bookName);
                    hashMap.put("uId",mUid);
                    hashMap.put("Author",authorName);

                    databaseReference.child(bookName).setValue(hashMap);
                }
            }
        });

        return view;
    }
}