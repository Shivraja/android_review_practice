package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by Shiv on 22-Oct-15.
 */
public class ReviewDataRetriever {

    Review review[];
    static MovieDatabaseHandler movieDatabaseHandler;
    int sampleUserImage[] = {R.drawable.karthi,R.drawable.surya, R.drawable.trisha, R.drawable.rajini,R.drawable.rajini_2};
    public void initialise(){


        String sampleUserId[] = {"Karthi","Surya","Trisha","Rajini","Bhaasha"};
        String sampleReviews[] = {"The Movie is nice, but the climax is okay","Thai herbs have increasingly gained public attention.Recently, there are a number of Thai herb websites. Each website" , "has similar information but quite different details. For example,some webpages do not provide information indicating which part" ,"of Thai herb can treat the specified symptom. In order to collect more complete Thai herb information, we have developed" ,"information extraction process to extract Thai herb information from multiple websites."};

        Log.w("REVIEW","Entered Review class");
        review = new Review[10];
        int i;
        for(i=0;i<10;i++){
            Review temp = new Review();
            temp.movieId = i;
            temp.likes = (int)(Math.random()*100);
            temp.unlikes = (int)(Math.random()*100);
            int x=((int)(Math.random()*100))%5;
            temp.userImage = sampleUserImage[x];
            temp.userId = sampleUserId[x];
            temp.review = sampleReviews[x];
            review[i] = temp;
        }
    }

    public ReviewDataRetriever(Context context, Resources resources){
        movieDatabaseHandler = new MovieDatabaseHandler(context,resources);
      //  initialise();
    }
    public Review[] getReviews(int movieId){
        Review[] reviews = movieDatabaseHandler.getReviews(movieId);
        for(int i=0; i< reviews.length; i++){
            int x=((int)(Math.random()*100))%5;
            reviews[i].userImage = sampleUserImage[x];
        }
        return reviews;
    }


}