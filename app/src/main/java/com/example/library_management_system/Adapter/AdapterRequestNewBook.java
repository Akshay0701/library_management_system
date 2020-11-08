package com.example.library_management_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.Model.RequestNewBook;
import com.example.library_management_system.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRequestNewBook  extends RecyclerView.Adapter<AdapterRequestNewBook.MyHolder>  {

    Context context;
    List<RequestNewBook> bookList;

    public AdapterRequestNewBook(Context context, List<RequestNewBook> schoolList) {
        this.context = context;
        this.bookList = schoolList;
    }

    @NonNull
    @Override
    public AdapterRequestNewBook.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_request_new_book,parent,false);
        return new AdapterRequestNewBook.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRequestNewBook.MyHolder holder, final int position) {
        //setdata
        holder.userName.setText("User ID : "+bookList.get(position).getuId());
        holder.bookAuthor.setText("Book Author : "+ bookList.get(position).getAuthor());
        holder.bookName.setText(bookList.get(position).getName());
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+bookList.get(position).getuId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView bookName,bookAuthor,userName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.bookName);
            bookAuthor=itemView.findViewById(R.id.bookAuthor);
            userName=itemView.findViewById(R.id.userName);
        }
    }

}
