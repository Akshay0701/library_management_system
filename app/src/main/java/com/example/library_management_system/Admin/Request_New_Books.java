package com.example.library_management_system.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.library_management_system.Adapter.AdapterBooks;
import com.example.library_management_system.Adapter.AdapterRequestNewBook;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.Model.RequestNewBook;
import com.example.library_management_system.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Request_New_Books extends AppCompatActivity {

    AdapterRequestNewBook adapterBookImg;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;

    List<RequestNewBook> bookList_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__new__books);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Requests");

        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(Request_New_Books.this);
        recyclerView.setLayoutManager(layoutManager);

        bookList_search = new ArrayList<>();
        loadBooks();
    }
    private void loadBooks() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RequestNewBook book = ds.getValue(RequestNewBook.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    bookList_search.add(book);
                }

                //adapter
                adapterBookImg = new AdapterRequestNewBook(Request_New_Books.this, bookList_search);
                recyclerView.setLayoutManager(new LinearLayoutManager(Request_New_Books.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterBookImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}