package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Shiv on 22-Oct-15.
 */
public class ReviewDataRetriever {

    static DatabaseHandler databaseHandler;
    int sampleUserImage[] = {R.drawable.karthi,R.drawable.surya, R.drawable.trisha, R.drawable.rajini,R.drawable.rajini_2};

    public ReviewDataRetriever(Context context, Resources resources){
        databaseHandler = new DatabaseHandler(context, resources);
      //  initialise();
    }

    public REVIEW[] getReviews(int movieId) {
        REVIEW[] reviews = databaseHandler.getReviews(movieId);
        for(int i=0; i< reviews.length; i++){
            int x=((int)(Math.random()*100))%5;
            reviews[i].userImage = sampleUserImage[x];
        }
        return reviews;
    }

}