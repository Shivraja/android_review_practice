package com.example.shiv.reelbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shiv on 22-Oct-15.
 */
public class ReviewListAdapter extends BaseAdapter {
    REVIEW reviews[];
    Context context;
    TextView userName, likes, unlikes, review;
    ImageView userImage;
    LayoutInflater layoutInflater;
    ImageView reviewLike, reviewUnlike;

    ReviewListAdapter(REVIEW reviews[], Context context) {
        this.reviews = reviews;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
       // Log.w("REVIEW Length", reviews.length + "");
        return reviews.length;
    }

    @Override
    public Object getItem(int i) {
        return reviews[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = layoutInflater.inflate(R.layout.review_list_layout,viewGroup,false);
        userName = (TextView)view.findViewById(R.id.user_name);
        review = (TextView)view.findViewById(R.id.review);
        likes = (TextView)view.findViewById(R.id.review_likes);
        unlikes = (TextView)view.findViewById(R.id.review_unlikes);
        userImage = (ImageView)view.findViewById(R.id.review_user_icon_image);
        reviewLike = (ImageView)view.findViewById(R.id.review_like_button);
        reviewUnlike = (ImageView)view.findViewById(R.id.review_unlike_button);

        userName.setText(reviews[i].userId);
        review.setText(reviews[i].review);
        likes.setText(reviews[i].likes + "");
        unlikes.setText(reviews[i].unlikes + "");
        userImage.setImageResource(reviews[i].userImage);

        reviewLike.setTag(i + "");
        reviewUnlike.setTag(i+"");

        reviewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = view.getRootView();
                Log.w("parent",parent+"");
                Log.w("REVIEW", view+"");
                ImageView imageView = (ImageView)view;
                imageView.setImageResource(R.drawable.a1);
                Log.w("REVIEW", view.getId()+ " " + view.getTag());
                Toast.makeText(context, view.getTag()+" ", Toast.LENGTH_SHORT).show();
            }
        });

        reviewUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = view.getRootView();
                Log.w("parent",parent+"");
                Log.w("REVIEW", view+"");
                ImageView imageView = (ImageView)view;
                imageView.setImageResource(R.drawable.a1);
                Log.w("REVIEW", view.getId()+ " " + view.getTag());
                Toast.makeText(context, view.getTag()+" ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}