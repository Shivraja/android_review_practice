package com.example.shiv.reelbox;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;


public class MoviesDataRetriever {
    static Movie[] movies;
    static Movie[] topPopularMovies = null;
    static Movie[] popularMovies = null;
    static Movie[] topRatedMovies = null;
    static Movie[] ratedMovies = null;
    static Movie[] topRecentMovies = null;
    static Movie[] recentMovies = null;
    static MovieDatabaseHandler databaseHandler;
    Resources resources;
    Context context;

    public void intialize(){
        int size = 17;
        movies = new Movie[size];
        List<Integer> casts = new ArrayList();
        casts.add(R.drawable.a1);
        casts.add(R.drawable.a2);
        casts.add(R.drawable.a4);

        List<String> links = new ArrayList();
        links.add("http://www.google.com");
        links.add("http://www.google.com");
        links.add("http://www.google.com");
        links.add("http://www.google.com");


        int iconImage[] = {R.drawable.yennaiarindhal_icon, R.drawable.anegan_icon, R.drawable.a2, R.drawable.a6,
        R.drawable.a2, R.drawable.jannalooram_icon, R.drawable.naveenasaraswathisabatham_icon, R.drawable.naiyaandi_icon, R.drawable.samar_icon, R.drawable.vanayuddham_icon, R.drawable.chennaiyilorunaal_icon, R.drawable.vanakkamchennai_icon, R.drawable.kadal_icon, R.drawable.udhayamnh4_icon, R.drawable.a7, R.drawable.rajarani_icon, R.drawable.vishwaroopam_icon, R.drawable.i_head};

        int headImage[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head,
                R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

        String movieName[] = {"Yennai Arindhal","Anegan", "Darling", "Jilla", "Paapanasam", "Jannal Ooram", "Naveena Saraswathi Sabatham", "Naiyaandi", "Samar", "Vana Yudham", "Chennaiyil Oru Naal", "Vanakkam Chennai", "Kadal", "Udhayam NH4", "Aarambam", "Rajarani", "Vishwaroopam", "I"};

        for(int i=0;i<17;i++){
            Movie temp = new Movie();
            temp.movieId = i;
            temp.movieName = movieName[i];
            temp.rating = ((int)(Math.random()*10))%5+1;
            temp.year = 2000 + (int)(Math.random()*10)%15;
            temp.language = "Tamil";
            temp.casts = casts; //
            temp.links = links; //
            temp.iconImageId = iconImage[i];
            temp.headImageId = headImage[i];
            temp.iconImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, iconImage[i], 100, 100);  //
            temp.headImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, headImage[i], 200, 150);  //
            movies[i] = temp;
        }
    }

    MoviesDataRetriever(Context context, Resources resources){
        //To include database commands here
        this.resources = resources;
        this.context = context;
        databaseHandler = new MovieDatabaseHandler(this.context,this.resources);
       // intialize();

    }

    public Movie[] optimizeImage(Movie[] movies){
        int size = movies.length;
        Links[] links;
        for(int i=0;i<size ; i++){
            links = databaseHandler.getLinks(movies[i].movieId);
            List<String> listLinks = new ArrayList();
            for(int j=0 ;j < links.length;j++){
                listLinks.add(links[j].linkURL);
            }
            movies[i].links = listLinks;
            movies[i].iconImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, movies[i].iconImageId, 50, 50);  //
            movies[i].headImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, movies[i].headImageId, 200, 150);  //
        }
        return movies;
    }

    public Movie[] getTopPopularMovies(String language){
        if(topPopularMovies == null){
            topPopularMovies = optimizeImage(databaseHandler.getTopPopularMovies(language));
        }
        return topPopularMovies;
    }

    public Movie[] getPopularMovies(String language){
        if(popularMovies == null){
            popularMovies = optimizeImage(databaseHandler.getPopularMovies(language));
        }
        return popularMovies;
    }

    public Movie[] getTopRatedMovies(String language){
        if(topRatedMovies == null){
            topRatedMovies = optimizeImage(databaseHandler.getTopRatedMovies(language));
        }
        return topRatedMovies;
    }

    public Movie[] getRatedMovies(String language){
        if(ratedMovies == null){
            ratedMovies = optimizeImage(databaseHandler.getRatedMovies(language));
        }
        return ratedMovies;
    }

    public Movie[] getTopRecentMovies(String language){
        if(topRecentMovies == null){
            topRecentMovies = optimizeImage(databaseHandler.getTopRecentMovies(language));
        }
        return topRecentMovies;
    }

    public Movie[] getRecentMovies(String language){
        if(recentMovies == null){
            recentMovies = optimizeImage(databaseHandler.getRecentMovies(language));
        }
        return recentMovies;
    }

    public Movie[] getFollowedMovies(String username){
        return optimizeImage(databaseHandler.getFollowedMovies(username));
    }

    public Movie getMovie(int movieId){
        Movie movie = databaseHandler.getMovie(movieId);
        Links[] links = databaseHandler.getLinks(movieId);
        List<String> listLinks = new ArrayList();
        for(int j=0 ;j < links.length;j++){
            listLinks.add(links[j].linkURL);
        }
        movie.links = listLinks;
        movie.iconImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, movie.iconImageId, 50, 50);  //
        movie.headImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, movie.headImageId, 200, 150);
        return movie;
    }

    public Movie[] getAllMovies(){
        return getRecentMovies("Tamil"); // Just a temporary one
    }

    public boolean isRated(int movieId, String username){
        return databaseHandler.isRated(movieId, username);
    }

    public boolean isFollowed(int movieId, String username){
        return databaseHandler.isFollowed(movieId, username);
    }

    public void addMovieRating(int movieId, String username, float rate){
        databaseHandler.addMovieRating(movieId, username, rate);
    }

    public void addFollow(int movieId, String username){
        databaseHandler.addFollow(movieId, username);
    }

    public void removeFollow(int movieId, String username){
        databaseHandler.removeFollow(movieId, username);
    }

    public void incrementViewCount(int movieId){
        databaseHandler.incrementViewCount(movieId);
    }
}