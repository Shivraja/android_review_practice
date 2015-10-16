package com.example.shiv.list_view_practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shiv on 02-Oct-15.
 */
public class CustomHeadAdapter extends BaseAdapter {
    String text1[],text2[];
    int image[];
    Context ctx;
    LayoutInflater layoutInflater;
    public CustomHeadAdapter(String name1[],String name2[],int images[],Context context) {
        text1 = name1;
        text2 = name2;
        ctx = context;
        image = images;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = layoutInflater.inflate(R.layout.headview_layout, viewGroup, false);
        view.setBackgroundResource(image[i]);
        ImageView imageView =(ImageView)view.findViewById(R.id.headImage);
        imageView.setImageResource(image[i]);
        TextView textView1= (TextView)view.findViewById(R.id.texts1);
        TextView textView2= (TextView)view.findViewById(R.id.texts2);
        textView1.setText(text1[i]);
        textView2.setText(text2[i]);
        return view;
    }
}
