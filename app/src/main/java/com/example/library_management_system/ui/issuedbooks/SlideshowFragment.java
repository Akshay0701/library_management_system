package com.example.library_management_system.ui.issuedbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management_system.Adapter.AdapterIssuedBooks;
import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.R;
import com.example.library_management_system.User.userRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    List<IssuedBook> bookList_issued;

    AdapterIssuedBooks adapterBookImg;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView_issued;
    RecyclerView.LayoutManager  layoutManager;

    String mUid;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
        }else{
            startActivity(new Intent(getContext(), userRegister.class));
            getActivity().finish();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_issuedbooks, container, false);

        bookList_issued = new ArrayList<>();

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("IssuedBooks/"+mUid);

        //load recycleBook
        recyclerView_issued =(RecyclerView)root.findViewById(R.id.recyclerView_issued);
        recyclerView_issued.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_issued.setLayoutManager(layoutManager);

        loadBooks();

        return root;
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
                    if(issuedBook.getuId().toString().equals(mUid)){
                        bookList_issued.add(issuedBook);
//                        Toast.makeText(getContext(), "woo", Toast.LENGTH_SHORT).show();//
                    }
                }

                //adapter
                adapterBookImg = new AdapterIssuedBooks(getActivity(), bookList_issued);
                recyclerView_issued.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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