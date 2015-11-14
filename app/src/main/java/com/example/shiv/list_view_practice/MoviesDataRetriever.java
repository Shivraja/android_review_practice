package com.example.shiv.list_view_practice;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;


public class MoviesDataRetriever {
    static Movies[] movieList;
    int size = 17;

    Resources resources;
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


        int iconImage[] = {R.drawable.yennaiarindhal_icon, R.drawable.anegan_icon, R.drawable.a2, R.drawable.a6,
        R.drawable.a2, R.drawable.jannalooram_icon, R.drawable.naveenasaraswathisabatham_icon, R.drawable.naiyaandi_icon, R.drawable.samar_icon, R.drawable.vanayuddham_icon, R.drawable.chennaiyilorunaal_icon, R.drawable.vanakkamchennai_icon, R.drawable.kadal_icon, R.drawable.udhayamnh4_icon, R.drawable.a7, R.drawable.rajarani_icon, R.drawable.vishwaroopam_icon, R.drawable.i_head};

        int headImage[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head,
                R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

        String movieName[] = {"Yennai Arindhal","Anegan", "Darling", "Jilla", "Paapanasam", "Jannal Ooram", "Naveena Saraswathi Sabatham", "Naiyaandi", "Samar", "Vana Yudham", "Chennaiyil Oru Naal", "Vanakkam Chennai", "Kadal", "Udhayam NH4", "Aarambam", "Rajarani", "Vishwaroopam", "I"};

        for(int i=0;i<17;i++){
            Movies temp = new Movies();
            temp.movieId = i;
            temp.movieName = movieName[i];
            temp.rating = ((int)(Math.random()*10))%5+1;
            temp.year = 2000 + (int)(Math.random()*10)%15;
            temp.language = "Tamil";
            temp.casts = casts;
            temp.links = links;
            temp.iconImageId = iconImage[i];
            temp.headImageId = headImage[i];
            temp.iconImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, iconImage[i], 100, 100);
            temp.headImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, headImage[i], 200, 150);
            movieList[i] = temp;
        }
    }
    MoviesDataRetriever(Resources resources){
        //To include database commands here
        this.resources = resources;
        intialize();
    }

    public int getSize(){
        return size;
    }

    public Movies[] getAllMovies(){
        return movieList;
    }


        /*  int image1[] = {R.drawable.a1,R.drawable.a2,R.drawable.a6,R.drawable.a4,R.drawable.a2,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a4,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17};

        int headImages[] = {R.drawable.head_1,R.drawable.head_2,R.drawable.head_3,R.drawable.head_4,R.drawable.head_5,R.drawable.head_6,R.drawable.head_7,R.drawable.head_8,R.drawable.head_9,R.drawable.head_10,R.drawable.head_abi,R.drawable.head_aranmanai,R.drawable.head_saivam,R.drawable.head_3,R.drawable.head_2,R.drawable.head_1,R.drawable.head_6,R.drawable.head_4,};
*/

}

