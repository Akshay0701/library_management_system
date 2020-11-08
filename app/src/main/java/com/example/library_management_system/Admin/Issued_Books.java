package com.example.library_management_system.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.library_management_system.Adapter.AdapterIssuedBooks;
import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Issued_Books extends AppCompatActivity {

    List<IssuedBook> bookList_issued;

    AdapterIssuedBooks adapterBookImg;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView_issued;
    RecyclerView.LayoutManager  layoutManager;

    String mUid;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued__books);

        bookList_issued = new ArrayList<>();

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("IssuedBooks/"+mUid);

        //load recycleBook
        recyclerView_issued =(RecyclerView)findViewById(R.id.recyclerView_issued);
        recyclerView_issued.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(Issued_Books.this);
        recyclerView_issued.setLayoutManager(layoutManager);

        loadBooks();
    }

    private void loadBooks() {
        //path
        Query ref = databaseReference=firebaseDatabase.getReference("IssuedBooks");;
        //get all data from this ref
        bookList_issued.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    IssuedBook issuedBook = ds.getValue(IssuedBook.class);
//                    book.setbId(ds.getKey());
                    //adding each object
//                    Toast.makeText(getContext(), "uid "+issuedBook.getuId()+"\n uid "+mUid, Toast.LENGTH_SHORT).show();
//                    if(issuedBook.getuId().toString().equals(mUid)){
                        bookList_issued.add(issuedBook);
//                        Toast.makeText(getContext(), "woo", Toast.LENGTH_SHORT).show();//
//                    }
                }

                //adapter
                adapterBookImg = new AdapterIssuedBooks(Issued_Books.this, bookList_issued);
                recyclerView_issued.setLayoutManager(new LinearLayoutManager(Issued_Books.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView_issued.setAdapter(adapterBookImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}