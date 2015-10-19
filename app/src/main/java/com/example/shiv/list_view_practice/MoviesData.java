package com.example.shiv.list_view_practice;

import java.util.ArrayList;
import java.util.List;


public class MoviesData {
    static Movies[] movieList;
    int size = 17;
    public void intialize(){
        movieList = new Movies[size];

        List<Integer> casts = new ArrayList();
        casts.add(R.drawable.a1);
        casts.add(R.drawable.a2);
        casts.add(R.drawable.a4);

        List<String> links = new ArrayList();
        links.add("http://www.google.com");
        links.add("http://www.google.com");
        links.add("http://www.google.com");
        links.add("http://www.google.com");

        int image1[] = {R.drawable.a1,R.drawable.a2,R.drawable.a6,R.drawable.a4,R.drawable.a2,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a4,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17};
        String movieNames[]={"Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja", "Kavi", "Praveena", "Raja", "Kavi","Pradeep", "Praveena", "Raja"};

        for(int i=0;i<17;i++){
            Movies temp = new Movies();
            temp.movieId = i;
            temp.movieName = movieNames[i];
            temp.rating = (Math.random()*10)%5+1;
            temp.year = 2000 + (int)(Math.random()*10)%15;
            temp.language = "Tamil";
            temp.casts = casts;
            temp.links = links;
            temp.iconImageId = image1[i];
            temp.headImageId = image1[i];
            movieList[i] = temp;
        }
    }
    MoviesData(){
        //To include database commands here
        intialize();
    }

    public int getSize(){
        return size;
    }

    public Movies[] getMovies(){
        return movieList;
    }
    public int[] getHeadImages(){
        int[] headImages = new int[size];
        for(int i=0 ;i < size ;i++)
            headImages[i] = movieList[i].headImageId;
        return headImages;
    }

    public int[] getIconImages(){
        int[] iconImages = new int[size];
        for(int i=0 ;i < size ; i++)
            iconImages[i]=movieList[i].iconImageId;
        return iconImages;
    }

    public String[] getMovieNames(){
        String[] movieNames = new String[size];
        for(int i=0 ;i < size ;i++)
            movieNames[i]=movieList[i].movieName;
        return movieNames;
    }

    public String[] getMovieLanguage(){
        String[] movieLanguage = new String[size];
        for(int i=0 ;i < size ;i++)
            movieLanguage[i]=movieList[i].language;
        return movieLanguage;
    }


    public double[] getMovieRatings(){
        double[] movieRatings = new double[size];
        for(int i=0 ;i < size ;i++)
            movieRatings[i]= movieList[i].rating;
        return movieRatings;
    }

    public int[] getMovieYear(){
        int[] movieRatings = new int[size];
        for(int i=0 ;i < size ;i++)
            movieRatings[i]= movieList[i].year;
        return movieRatings;
    }

    public Movies getClass(int i){
        return movieList[i];
    }
}
