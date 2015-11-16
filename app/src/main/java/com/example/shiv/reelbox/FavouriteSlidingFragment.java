package com.example.shiv.reelbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Shiv on 31-Oct-15.
 */
public class FavouriteSlidingFragment extends Fragment {

    static Movie[] favouriteMovies = null;
    static FavouriteMovieListAdapter listAdapter;
    ListView listView;
    ImageView imageView;
    static MoviesDataRetriever moviesDataRetriever;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_sliding_fragment,container,false);
        moviesDataRetriever = new MoviesDataRetriever(getActivity(),getResources());

        listView = (ListView)view.findViewById(R.id.favourite_list_view);
        imageView = (ImageView)view.findViewById(R.id.favourite_background_image);

        if (CONSTANTS.BACKGROUND_IMAGE == null)
            CONSTANTS.BACKGROUND_IMAGE = ImageOptimizer.getCorrespondingBitmap(getResources(), R.drawable.skin_full_page_bgimage_a, 200, 500);

        imageView.setImageBitmap(CONSTANTS.BACKGROUND_IMAGE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startMoviesActivity(favouriteMovies[i].movieId);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CONSTANTS.FAVOURITE_ALTERED || favouriteMovies==null) {
            favouriteMovies = moviesDataRetriever.getFollowedMovies(CONSTANTS.USER_NAME);
            CONSTANTS.FAVOURITE_ALTERED = false;
            listAdapter = new FavouriteMovieListAdapter(favouriteMovies,getResources(),getActivity());
        }
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onStop() {
        super.onPause();
    }

    public void startMoviesActivity(int movieId) {
        Intent moviesActivity = new Intent(getActivity(), MovieActivity.class);
        moviesActivity.putExtra("movieId", movieId);
        moviesDataRetriever.incrementViewCount(movieId);
        startActivity(moviesActivity);
    }
}
