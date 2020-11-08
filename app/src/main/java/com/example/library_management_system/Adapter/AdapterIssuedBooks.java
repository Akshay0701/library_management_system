package com.example.library_management_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_management_system.Model.IssuedBook;
import com.example.library_management_system.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterIssuedBooks extends RecyclerView.Adapter<AdapterIssuedBooks.MyHolder>  {

    Context context;
    List<IssuedBook> bookList;

    public AdapterIssuedBooks(Context context, List<IssuedBook> schoolList) {
        this.context = context;
        this.bookList = schoolList;
    }

    @NonNull
    @Override
    public AdapterIssuedBooks.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_books,parent,false);
        return new AdapterIssuedBooks.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIssuedBooks.MyHolder holder, final int position) {
        final String bId= bookList.get(position).getbId();
        final String name= bookList.get(position).getBookName();
        final String issuedDate= bookList.get(position).getIssuedDate();
        final String dueDate= bookList.get(position).getDueDate();
        final String imageUrl= bookList.get(position).getImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.bookIssuedDate.setText("Issued Date : "+issuedDate);
        holder.bookDueDate.setText("Due Date : "+ dueDate);
        holder.bookName.setText(""+name);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+bookList.get(position).getbId(), Toast.LENGTH_SHORT).show();
                //handle when click on user
                //goto posts pages
//                Intent yourIntent = new Intent(context, BookDetails.class);
//                Bundle b = new Bundle();
//                b.putSerializable("BookObject",  bookList.get(position));
//                yourIntent.putExtras(b); //pass bundle to your intent
//                context.startActivity(yourIntent);
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
