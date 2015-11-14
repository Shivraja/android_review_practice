package com.example.shiv.reelbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shiv on 22-Oct-15.
 */
public class ReviewListAdapter extends BaseAdapter {
    Review reviewArray[];
    Context context;
    TextView userName, likes, unlikes, review;
    ImageView userImage;
    LayoutInflater layoutInflater;
    ReviewListAdapter(Review reviews[], Context context){
        this.reviewArray = reviews;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.w("Review Length",reviewArray.length+"");
        return reviewArray.length;
    }

    @Override
    public Object getItem(int i) {
        return reviewArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = layoutInflater.inflate(R.layout.review_list_layout,viewGroup,false);

        Log.w("REVIEW LIST",i+"");
        userName = (TextView)view.findViewById(R.id.user_name);
        review = (TextView)view.findViewById(R.id.review);
        likes = (TextView)view.findViewById(R.id.review_likes);
        unlikes = (TextView)view.findViewById(R.id.review_unlikes);
        userImage = (ImageView)view.findViewById(R.id.review_user_icon_image);

        userName.setText(reviewArray[i].userId);
        review.setText(reviewArray[i].review);
        likes.setText(reviewArray[i].likes+"");
        unlikes.setText(reviewArray[i].unlikes+"");
        userImage.setImageResource(reviewArray[i].userImage);

        return view;
    }
}