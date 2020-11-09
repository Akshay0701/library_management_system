package com.example.library_management_system.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Adapter.CustomGrid;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.Model.IssueRequest;
import com.example.library_management_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {

    ImageView img,imgLocation;
    TextView bookId,bookName,bookAuthor,bookAvai,bookLocation;
    EditText numberOfBooks;
    Button user_requestBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    LinearLayout requestBox;

    GridView gridView;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        assert bundle != null;
        book = (Book) bundle.getSerializable("BookObject");
        gridView=(GridView)findViewById(R.id.gridview);
        bookLocation=findViewById(R.id.bookLocation);
        bookAvai=findViewById(R.id.bookAvai);
        bookAuthor=findViewById(R.id.bookAuthor);
        bookName=findViewById(R.id.bookName);
        bookId=findViewById(R.id.bookId);
        img=findViewById(R.id.img);
        numberOfBooks=findViewById(R.id.numberOfBooks);
        user_requestBtn=findViewById(R.id.user_requestBtn);
        requestBox=findViewById(R.id.requestBox);
//        imgLocation=findViewById(R.id.imgLocation);

        if(book.getAvailable().equals("No")){
            requestBox.setVisibility(View.GONE);
        }

        //set
        bookLocation.setText("Location : "+book.getLocation());
        bookAvai.setText("Available : "+ book.getAvailable());
        bookAuthor.setText("Author : "+book.getAuthor());
        bookName.setText("Book Name : "+book.getName());
        bookId.setText("Book Id : "+book.getbId());
        Picasso.get().load(book.getImageUrl()).into(img);
//        //setting location img
//        Resources res = getResources();
//        String mDrawableName = "location"+book.getLocation();
//        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
//        Drawable drawable = res.getDrawable(resID );
//        imgLocation.setImageDrawable(drawable);

        //set girdview to show location of book
        setlocation();

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("IssueRequest");

        user_requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numDays=numberOfBooks.getText().toString();
                if(numDays.isEmpty()){
                    Toast.makeText(BookDetails.this, "Fill Number Of Days", Toast.LENGTH_SHORT).show();
                }else{
                    IssueRequest issueRequest=new IssueRequest(book.getImageUrl(),book.getAuthor(),book.getbId(),book.getName(),numDays,mUid,mEmail);
                    databaseReference.child(mUid).setValue(issueRequest);
                    Toast.makeText(BookDetails.this, "Request Sended", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setlocation() {
        CustomGrid adapter = new CustomGrid(BookDetails.this, book.getLocation(), 12);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(BookDetails.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
            mEmail=mAuth.getCurrentUser().getEmail().toString();
        }else{
            startActivity(new Intent(BookDetails.this, userRegister.class));
            finish();
        }
    }

}