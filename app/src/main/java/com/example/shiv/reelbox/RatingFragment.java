package com.example.shiv.reelbox;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by Shiv on 16-Nov-15.
 */
public class RatingFragment extends Fragment {
    MoviesDataRetriever moviesDataRetriever;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moviesDataRetriever = new MoviesDataRetriever(getActivity(),getResources());
        View view = inflater.inflate(R.layout.rating_fragment,container,false);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.user_movie_rate);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getActivity(),"Your Rating added Successfully",Toast.LENGTH_SHORT).show();
                if(!moviesDataRetriever.isRated(MovieActivity.movieId,CONSTANTS.USER_NAME))
                    moviesDataRetriever.addMovieRating(MovieActivity.movieId,CONSTANTS.USER_NAME,ratingBar.getRating());
            }
        });
        return view;
    }
}
