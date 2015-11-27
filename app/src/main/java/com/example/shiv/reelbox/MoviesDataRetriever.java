package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoviesDataRetriever {
    static Map<Integer, Bitmap> headImageMap = new HashMap<>();
    static Map<Integer, Bitmap> iconImageMap = new HashMap<>();
    static Map<Integer, Bitmap> castImageMap = new HashMap<>();
    static Map<Integer, MOVIE> movieMap = new HashMap<>();
    static Map<Integer, String> reviewMap = new HashMap<>();
    static MOVIE[] topPopularMovies = null;
    static MOVIE[] popularMovies = null;
    static MOVIE[] topRatedMovies = null;
    static MOVIE[] ratedMovies = null;
    static MOVIE[] topRecentMovies = null;
    static MOVIE[] recentMovies = null;
    static DatabaseHandler databaseHandler;
    Resources resources;
    Context context;

    Bitmap getOptimisedHeadImage(int resourseId) {
        if (!headImageMap.containsKey(resourseId)) {
            Bitmap bitmap = ImageOptimizer.getCorrespondingBitmap(resources, resourseId, 200, 150);
            headImageMap.put(resourseId, bitmap);
        }
        return headImageMap.get(resourseId);
    }

    Bitmap getOptimisedIconImage(int resourseId) {
        if (!iconImageMap.containsKey(resourseId)) {
            Bitmap bitmap = ImageOptimizer.getCorrespondingBitmap(resources, resourseId, 50, 50);
            iconImageMap.put(resourseId, bitmap);
        }
        return iconImageMap.get(resourseId);
    }

    Bitmap getOptimisedCastImage(int resourseId) {
        if (!castImageMap.containsKey(resourseId)) {
            Bitmap bitmap = ImageOptimizer.getCorrespondingBitmap(resources, resourseId, 200, 200);
            castImageMap.put(resourseId, bitmap);
        }
        return castImageMap.get(resourseId);
    }

    MoviesDataRetriever(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
        databaseHandler = new DatabaseHandler(this.context, this.resources);

    }

    public MOVIE[] optimizeImage(MOVIE[] movies) {
        int size = movies.length;
        LINKS[] links;
        for (int i = 0; i < size; i++) {
            movies[i].iconImageBitmap = getOptimisedIconImage(movies[i].iconImageId);
            movies[i].headImageBitmap = getOptimisedHeadImage(movies[i].headImageId);
        }
        return movies;
    }

    public MOVIE[] getTopPopularMovies(String language) {
        if (topPopularMovies == null) {
            topPopularMovies = optimizeImage(databaseHandler.getTopPopularMovies(language));
        }
        return topPopularMovies;
    }

    public MOVIE[] getPopularMovies(String language) {
        if (popularMovies == null) {
            popularMovies = optimizeImage(databaseHandler.getPopularMovies(language));
        }
        return popularMovies;
    }

    public MOVIE[] getTopRatedMovies(String language) {
        if (topRatedMovies == null) {
            topRatedMovies = optimizeImage(databaseHandler.getTopRatedMovies(language));
        }
        return topRatedMovies;
    }

    public MOVIE[] getRatedMovies(String language) {
        if (ratedMovies == null) {
            ratedMovies = optimizeImage(databaseHandler.getRatedMovies(language));
        }
        return ratedMovies;
    }

    public MOVIE[] getTopRecentMovies(String language) {
        if (topRecentMovies == null) {
            topRecentMovies = optimizeImage(databaseHandler.getTopRecentMovies(language));
        }
        return topRecentMovies;
    }

    public MOVIE[] getRecentMovies(String language) {
        if (recentMovies == null) {
            recentMovies = optimizeImage(databaseHandler.getRecentMovies(language));
        }
        return recentMovies;
    }

    public MOVIE[] getFollowedMovies(String username) {
        return optimizeImage(databaseHandler.getFollowedMovies(username));
    }

    public MOVIE getMovie(int movieId) {
        if(movieMap.containsKey(movieId))
            return movieMap.get(movieId);
        MOVIE MOVIE = databaseHandler.getMovie(movieId);
        LINKS[] links = databaseHandler.getLinks(movieId);
        List<String> listLinks = new ArrayList();

        for (int j = 0; j < links.length; j++) {
            listLinks.add(links[j].linkURL);
        }

        MOVIE.links = listLinks;
        CASTS casts[] = databaseHandler.getCasts(movieId);
        int size = casts.length;
        for (int i = 0; i < size; i++) {
            casts[i].imageBitmap = getOptimisedCastImage(casts[i].imageId);
        }

        MOVIE.casts = casts;
        MOVIE.iconImageBitmap = getOptimisedIconImage(MOVIE.iconImageId);
        MOVIE.headImageBitmap = getOptimisedHeadImage(MOVIE.headImageId);
        movieMap.put(movieId, MOVIE);
        return MOVIE;
    }

    public MOVIE[] getAllMovies() {
        return getRecentMovies("Tamil"); // Just a temporary one
    }

    public boolean isRated(int movieId, String username) {
        return databaseHandler.isRated(movieId, username);
    }

    public boolean isFollowed(int movieId, String username) {
        return databaseHandler.isFollowed(movieId, username);
    }

    public void addMovieRating(int movieId, String username, float rate) {
        databaseHandler.addMovieRating(movieId, username, rate);
    }

    public void addFollow(int movieId, String username) {
        databaseHandler.addFollow(movieId, username);
    }

    public void removeFollow(int movieId, String username) {
        databaseHandler.removeFollow(movieId, username);
    }

    public CASTS[] getCasts(int movieId) {
        CASTS casts[] = databaseHandler.getCasts(movieId);
        int size = casts.length;
        for (int i = 0; i < size; i++) {
            casts[i].imageBitmap = getOptimisedCastImage(casts[i].imageId);
        }
        return casts;
    }

    public String getReview(int movieId, String username){
        if(reviewMap.containsKey(movieId)){
            return reviewMap.get(movieId);
        }
        String userReview;
        REVIEW review = databaseHandler.getReview(movieId, username);
        if(review==null)
            userReview =  "-1";
        else
            userReview = review.review;

        reviewMap.put(movieId,userReview);
        return userReview;
    }

    public void removeMovieReview(int movieId, String username){
        //databaseHandler.removeMovieReview(movieId, username);                *****
        reviewMap.put(movieId,"-1");
    }

    public void addOrUpdateMovieReview(int movieId, String username, String review){
        if(reviewMap.containsKey(movieId) && !reviewMap.get(movieId).equals("-1")){
            //databaseHandler.updateMovieReview(movieId, username, review);  *****
            reviewMap.put(movieId,review);
        }else{
            //databaseHandler.addMovieReview(movieId, username, review);     *****
        }
        reviewMap.put(movieId,review);
    }

    public void incrementViewCount(int movieId) {
        databaseHandler.incrementViewCount(movieId);
    }

    public boolean isValidUser(String username, String password) {
        return username.matches("Raja") && password.matches("raja");
    }

    public void optimiseCastImage(){
        int castsImageId[] = databaseHandler.getCelebritiesImageId();
        int length = castsImageId.length;
        for(int i=0; i<length; i++){
            getOptimisedCastImage(castsImageId[i]);
        }
    }
}