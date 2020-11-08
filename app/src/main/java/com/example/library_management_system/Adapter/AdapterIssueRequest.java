package com.example.library_management_system.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Admin.IssueRequestDetails;
import com.example.library_management_system.Model.IssueRequest;
import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.R;
import com.example.library_management_system.User.BookDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterIssueRequest  extends RecyclerView.Adapter<AdapterIssueRequest.MyHolder>  {

    Context context;
    List<IssueRequest> bookList;

    public AdapterIssueRequest(Context context, List<IssueRequest> schoolList) {
        this.context = context;
        this.bookList = schoolList;
    }

    @NonNull
    @Override
    public AdapterIssueRequest.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_books,parent,false);
        return new AdapterIssueRequest.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIssueRequest.MyHolder holder, final int position) {
        final String name= bookList.get(position).getBookName();
        final String userEmail= bookList.get(position).getUserEmail();
        final String dueDate="Requested For "+bookList.get(position).getNumDays()+" Days";
        final String imageUrl= bookList.get(position).getImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.bookIssuedDate.setText("User Email : "+userEmail);
        holder.bookDueDate.setText(dueDate);
        holder.bookName.setText(name);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+bookList.get(position).getbId(), Toast.LENGTH_SHORT).show();
                //handle when click on user
                //goto posts pages
                Intent yourIntent = new Intent(context, IssueRequestDetails.class);
                Bundle b = new Bundle();
                b.putSerializable("BookObject",  bookList.get(position));
                yourIntent.putExtras(b); //pass bundle to your intent
                context.startActivity(yourIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView bookName,bookIssuedDate,bookDueDate;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            bookName=itemView.findViewById(R.id.bookName);
            bookIssuedDate=itemView.findViewById(R.id.bookAuthor);
            bookDueDate=itemView.findViewById(R.id.bookAvai);
        }
    }

}
