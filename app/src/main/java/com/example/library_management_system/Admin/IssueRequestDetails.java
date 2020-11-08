package com.example.library_management_system.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Model.Book;
import com.example.library_management_system.Model.IssueRequest;
import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.R;
import com.example.library_management_system.User.BookDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IssueRequestDetails extends AppCompatActivity {

    ImageView img;
    TextView bookId,bookName,numDays,issueDate,dueDate,userEmail;
    EditText numberOfBooks;
    Button issueBookBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail,dueDateStr,issueDateStr;
    private FirebaseAuth mAuth;

    IssueRequest book;

    LinearLayout requestBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_request_details);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        assert bundle != null;
        book = (IssueRequest) bundle.getSerializable("BookObject");

        numDays=findViewById(R.id.numDays);
        issueDate=findViewById(R.id.issueDate);
        dueDate=findViewById(R.id.dueDate);
        bookName=findViewById(R.id.bookName);
        bookId=findViewById(R.id.bookId);
        img=findViewById(R.id.img);
        userEmail=findViewById(R.id.userEmail);
        issueBookBtn=findViewById(R.id.issueBookBtn);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("IssuedBooks");

        //set issued Date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        issueDateStr=dateFormat.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try{
            //Setting the date to the given date
            c.setTime(sdf.parse(issueDateStr));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(book.getNumDays()));
        //Date after adding the days to the given date
        dueDateStr = sdf.format(c.getTime());

        //set
        issueDate.setText("Issue Date : "+issueDateStr);
        dueDate.setText("Due Date : "+ dueDateStr);
        userEmail.setText("User Email : "+book.getUserEmail());
        bookName.setText("Book Name : "+book.getBookName());
        bookId.setText("Book Id : "+book.getbId());
        Picasso.get().load(book.getImageUrl()).into(img);

        issueBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueRequestDetails.this,4);
                alertDialogBuilder.setMessage("Do You Want To Issue This Book To User");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            final ProgressDialog pd=new ProgressDialog(IssueRequestDetails.this);
                            pd.setMessage("Issued Book..");
                            IssuedBook issuedBook=new IssuedBook(book.getImageUrl(),book.getAuthorName(),book.getbId(),book.getBookName(),dueDateStr,issueDateStr,book.getuId(),book.getUserEmail());
                            databaseReference.child(book.getbId()+book.getuId()).setValue(issuedBook);
                            //image
                            Query fquery= FirebaseDatabase.getInstance().getReference("IssueRequest").orderByChild("uId").equalTo(book.getuId());
                            fquery.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                        ds.getRef().removeValue();
                                    }
                                    sendMailToUser();
//                                        Toast.makeText(IssueRequestDetails.this, "Deleted Book", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    Toast.makeText(IssueRequestDetails.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(IssueRequestDetails.this,AdminDashBorad.class));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
             }
         });
    }
    private void sendMailToUser() {
        String email=book.getUserEmail();
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();

        String subject=book.getBookName()+" Book Issued By Admin ";
        String message="Hello"+
                "\n" +
                "Your Requested Book "+ book.getBookName()+" For "+book.getNumDays()+" Days is Issued By Admin Kindly Collect From Library" +"\n"+
                "\n" +
                "Thank You "+
                "\n" +
                "\n" +
                "Your project-576045884629 team";
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,email,subject,message);
        javaMailAPI.execute();

    }
}
