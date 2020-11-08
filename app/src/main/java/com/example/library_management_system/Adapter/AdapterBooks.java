package com.example.library_management_system.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Admin.AdminDashBorad;
import com.example.library_management_system.Admin.Update_Book_Detail;
import com.example.library_management_system.User.BookDetails;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.MyHolder>  {




    Context context;
    List<Book> bookList;
    boolean isUser;
    String action;
    public AdapterBooks(Context context, List<Book> schoolList,boolean isUser,String action) {
        this.context = context;
        this.bookList = schoolList;
        this.isUser=isUser;
        this.action=action;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_books,parent,false);
        return new AdapterBooks.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String bId= bookList.get(position).getbId();
        final String name= bookList.get(position).getName();
        final String author= bookList.get(position).getAuthor();
        final String available= bookList.get(position).getAvailable();
        final String imageUrl= bookList.get(position).getImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.bookAuthor.setText("Author Name : "+author);
        holder.bookAvai.setText("Available : "+ available);
        holder.bookName.setText("Book Name : "+name);
        //handle click
        if(isUser){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //handle when click on user
                    //goto posts pages
                    Intent yourIntent = new Intent(context, BookDetails.class);
                    Bundle b = new Bundle();
                    b.putSerializable("BookObject",  bookList.get(position));
                    yourIntent.putExtras(b); //pass bundle to your intent
                    context.startActivity(yourIntent);
                }
            });
        }else if(action.equals("update")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //handle when click on user
                    //goto posts pages
                    Intent yourIntent = new Intent(context, Update_Book_Detail.class);
                    Bundle b = new Bundle();
                    b.putSerializable("BookObject",  bookList.get(position));
                    yourIntent.putExtras(b); //pass bundle to your intent
                    context.startActivity(yourIntent);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    showDialog(bookList.get(position));
//                    return false;
//                }
//            });
        }else if(action.equals("remove")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(bookList.get(position));
                }
            });
        }else if(action.equals("view")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+bookList.get(position).getbId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showDialog(final Book book) {
        //this function is for removing book from database
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,4);
        alertDialogBuilder.setMessage("Do You Want To Delete This Book");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                final ProgressDialog pd=new ProgressDialog(context);
                                pd.setMessage("Deleting..");

                                StorageReference picRef= FirebaseStorage.getInstance().getReferenceFromUrl(book.getImageUrl());
                                picRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        //image
                                        Query fquery= FirebaseDatabase.getInstance().getReference("Books").orderByChild("bId").equalTo(book.getbId());
                                        fquery.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                    ds.getRef().removeValue();
                                                }
                                                Toast.makeText(context, "Deleted Book", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                                context.startActivity(new Intent(context, AdminDashBorad.class));
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView bookName,bookAuthor,bookAvai;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            bookName=itemView.findViewById(R.id.bookName);
            bookAuthor=itemView.findViewById(R.id.bookAuthor);
            bookAvai=itemView.findViewById(R.id.bookAvai);
        }
    }

}
