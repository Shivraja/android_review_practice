package com.example.shiv.list_view_practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shiv on 28-Sep-15.
 */
public class CustomAdapter extends BaseAdapter {
    String text1[],text2[];
    int image[];
    Context ctx;
    LayoutInflater layoutInflater;
    public CustomAdapter(String name1[],String name2[],int images[],Context context) {
        text1 = name1;
        text2 = name2;
        ctx = context;
        image = images;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return text1.length;
    }

    @Override
    public Object getItem(int i) {
        return text1[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = layoutInflater.inflate(R.layout.list_view, viewGroup, false);
        ImageView back =(ImageView)view.findViewById(R.id.background);
        ImageView icon =(ImageView)view.findViewById(R.id.icon);
        back.setImageResource(image[i]);
        icon.setImageResource(image[i]);
        TextView textView1= (TextView)view.findViewById(R.id.texts1);
        TextView textView2= (TextView)view.findViewById(R.id.texts2);
        textView1.setText(text1[i]+"(Tamil)");
        textView2.setText(text2[i]);
        return view;
    }
}