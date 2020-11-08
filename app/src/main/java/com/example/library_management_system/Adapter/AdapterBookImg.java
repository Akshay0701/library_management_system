package com.example.library_management_system.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.library_management_system.User.BookDetails;
import com.example.library_management_system.Model.Book;
import com.example.library_management_system.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBookImg extends RecyclerView.Adapter<AdapterBookImg.MyHolder>  {

    Context context;
    List<Book> bookList;

    public AdapterBookImg(Context context, List<Book> schoolList) {
        this.context = context;
        this.bookList = schoolList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_image,parent,false);
        return new AdapterBookImg.MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String bId= bookList.get(position).getbId();
        final String imageUrl= bookList.get(position).getImageUrl();


        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        //handle click

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
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);

        }
    }

}
