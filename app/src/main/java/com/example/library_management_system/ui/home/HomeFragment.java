package com.example.library_management_system.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_management_system.Adapter.AdapterBookImg;
import com.example.library_management_system.Adapter.AdapterBooks;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView,recyclerView_more;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
//    RecyclerView recyclerViewTrendProduct;

    List<Book> bookList;
//    List<Foods> trendList;
    AdapterBookImg adapterBookImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        checkforuserlogin();
        bookList = new ArrayList<>();
//        trendList=new ArrayList<>();
//        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        //getContext.setS(toolbar);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Books");

        //    RecyclerView recyclerView;
        //    RecyclerView.LayoutManager  layoutManager;
      /*  DrawerLayout drawer = (DrawerLayout) root.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       */

        //name for User in menu

        //load recycleBook
        recyclerView =(RecyclerView)root.findViewById(R.id.recyclerView_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //load recycleBook
        recyclerView_more =(RecyclerView)root.findViewById(R.id.recyclerView_more);
        recyclerView_more.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_more.setLayoutManager(layoutManager);

        loadBooks();
//        loadTrend();


        return root;
    }

    private void loadBooks() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Books");
        //get all data from this ref
        bookList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = ds.getValue(Book.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    bookList.add(book);
                }

                //adapter
                adapterBookImg = new AdapterBookImg(getActivity(), bookList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView_more.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterBookImg);
                recyclerView_more.setAdapter(adapterBookImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkforuserlogin() {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //  token=FirebaseInstanceId.getInstance().getToken();
            String mUID = user.getUid();
        }
    }

}