package com.example.library_management_system.ui.Searchbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management_system.Adapter.AdapterBooks;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    EditText search_field;
    ImageButton search_btn;
    List<Book> bookList_search;

    AdapterBooks adapterBookImg;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView_search;
    RecyclerView.LayoutManager  layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        search_field=root.findViewById(R.id.search_field);
        search_btn=root.findViewById(R.id.search_btn);

        bookList_search = new ArrayList<>();

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Books");

        //load recycleBook
        recyclerView_search =(RecyclerView)root.findViewById(R.id.recyclerView_search);
        recyclerView_search.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_search.setLayoutManager(layoutManager);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = search_field.getText().toString();
                firebaseUserSearch(searchText);

            }
        });

        return root;
    }

    private void firebaseUserSearch(String searchText) {
        Toast.makeText(getContext(), "Started Search", Toast.LENGTH_LONG).show();

        Query ref = databaseReference.orderByChild("Name").startAt(searchText).endAt(searchText + "\uf8ff");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    bookList_search.add(book);
                }

                //adapter
                adapterBookImg = new AdapterBooks(getActivity(), bookList_search,true,"");
                recyclerView_search.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView_search.setAdapter(adapterBookImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}