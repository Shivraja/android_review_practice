package com.example.shiv.reelbox;

import android.content.Context;
import android.graphics.Color;
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
    REVIEW reviews[];
    Context context;
    TextView userName, likes, unlikes, review;
    ImageView userImage;
    LayoutInflater layoutInflater;
    ImageView reviewLike, reviewUnlike;
    ReviewDataRetriever dataRetriever;

    ReviewListAdapter(REVIEW reviews[], ReviewDataRetriever dataRetriever, Context context) {
        this.reviews = reviews;
        this.context = context;
        this.dataRetriever = dataRetriever;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (view == null)
            view = layoutInflater.inflate(R.layout.review_list_layout, viewGroup, false);
        userName = (TextView) view.findViewById(R.id.user_name);
        review = (TextView) view.findViewById(R.id.review);
        likes = (TextView) view.findViewById(R.id.review_likes);
        unlikes = (TextView) view.findViewById(R.id.review_unlikes);
        userImage = (ImageView) view.findViewById(R.id.review_user_icon_image);
        reviewLike = (ImageView) view.findViewById(R.id.review_like_button);
        reviewUnlike = (ImageView) view.findViewById(R.id.review_unlike_button);

        userName.setText(reviews[i].userId);
        review.setText(reviews[i].review);
        likes.setText(reviews[i].likes + "");
        unlikes.setText(reviews[i].unlikes + "");
        userImage.setImageResource(reviews[i].userImage);

        reviewLike.setTag(i + "");
        reviewUnlike.setTag(i + "");
        reviewLike.setBackgroundColor(Color.TRANSPARENT);
        reviewUnlike.setBackgroundColor(Color.TRANSPARENT);

        String status = dataRetriever.getReviewStatus(reviews[i].reviewId, reviews[i].movieId, CONSTANTS.USER_NAME);
        Log.w("STATUS", i + " " + status);
        if (status == null) {
            reviewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackgroundColor(Color.parseColor("#023119"));
                    int index = Integer.parseInt(view.getTag().toString());
                    dataRetriever.addReviewStatus(reviews[index].reviewId, CONSTANTS.USER_NAME, reviews[index].movieId, "LIKE");
                    reviewUnlike.setOnClickListener(null);
                    reviewUnlike.setClickable(false);
                }
            });

            reviewUnlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setBackgroundColor(Color.parseColor("#cd0000"));
                    int index = Integer.parseInt(view.getTag().toString());
                    dataRetriever.addReviewStatus(reviews[index].reviewId, CONSTANTS.USER_NAME, reviews[index].movieId, "UNLIKE");
                    reviewLike.setOnClickListener(null);
                    reviewLike.setClickable(false);
                }
            });
        } else {
            if (status.equals("LIKE")) {
                reviewLike.setBackgroundColor(Color.parseColor("#023119"));
                reviewLike.setClickable(false);
                reviewLike.setOnClickListener(null);
                reviewUnlike.setClickable(false);
                reviewUnlike.setOnClickListener(null);
            }else if (status.equals("UNLIKE")) {
                reviewUnlike.setBackgroundColor(Color.parseColor("#cd0000"));
                reviewUnlike.setClickable(false);
                reviewUnlike.setOnClickListener(null);
                reviewLike.setClickable(false);
                reviewLike.setOnClickListener(null);
            } else {
                reviewUnlike.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        return view;
    }
}