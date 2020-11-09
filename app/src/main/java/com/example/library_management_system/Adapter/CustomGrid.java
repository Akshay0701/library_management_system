package com.example.library_management_system.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library_management_system.R;
import com.example.library_management_system.User.BookDetails;

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private String location;
    private int size;
    public CustomGrid(Context c,String location,int size) {
        mContext = c;
        this.location=location;
        this.size=size;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.row_drawerlocation, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.drawerImg);
            //setting location img
//            Resources res = mContext.getResources();
//            String mDrawableName = "location"+book.getLocation();
//            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
//            Drawable drawable = res.getDrawable(resID );
            if(position+1==Integer.parseInt(location)){
                imageView.setImageResource(R.drawable.ondrawer);
            }else{
                imageView.setImageResource(R.drawable.offdrawer);
            }
//            imgLocation.setImageDrawable(drawable);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}