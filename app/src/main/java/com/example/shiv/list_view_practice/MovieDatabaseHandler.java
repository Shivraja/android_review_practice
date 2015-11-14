package com.example.shiv.list_view_practice;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiv on 07-Nov-15.
 */
public class MovieDatabaseHandler extends SQLiteOpenHelper {

    Resources resources;
    static String DATABASE_NAME = "movies.db";
    static int DATABASE_VERSION = 1;
    static String MOVIE_TABLE = "movies";
    static String CASTS_TABLE = "casts";
    static String ROLE_TABLE = "roles";
    static String LINKS_TABLE = "links";
    static String RATING_TABLE = "rating";
    static String FOLLOW_TABLE = "follow";
    static String REVIEW_TABLE = "reviews";
    static String USER_TABLE = "user";
    static String REVIEW_LIKE_TABLE = "reviewlike";

    public MovieDatabaseHandler(Context context, Resources resources) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.resources = resources;
    }

    public void insertValues(SQLiteDatabase sqLiteDatabase) {
        initMovies(sqLiteDatabase);
        initReviews(sqLiteDatabase);
    }

    public void initMovies(SQLiteDatabase sqLiteDatabase) {
        List<Integer> casts = new ArrayList();
        casts.add(R.drawable.a1);
        casts.add(R.drawable.a2);
        casts.add(R.drawable.a4);

        List<String> links = new ArrayList();
        links.add("http://www.google.com");
        links.add("http://www.youtube.com");
        links.add("http://www.quora.com");
        links.add("http://www.geeksforgeeks.org");

        String movieName[] = {"Yennai Arindhal", "Anegan", "Darling", "Jilla", "Paapanasam", "Jannal Ooram", "Naveena Saraswathi Sabatham", "Naiyaandi", "Samar", "Vana Yudham", "Chennaiyil Oru Naal", "Vanakkam Chennai", "Kadal", "Udhayam NH4", "Aarambam", "Rajarani", "Vishwaroopam", "I"};
        int iconImage[] = {R.drawable.yennaiarindhal_icon, R.drawable.anegan_icon, R.drawable.a2, R.drawable.a6,
                R.drawable.a2, R.drawable.jannalooram_icon, R.drawable.naveenasaraswathisabatham_icon, R.drawable.naiyaandi_icon, R.drawable.samar_icon, R.drawable.vanayuddham_icon, R.drawable.chennaiyilorunaal_icon, R.drawable.vanakkamchennai_icon, R.drawable.kadal_icon, R.drawable.udhayamnh4_icon, R.drawable.arambam_head, R.drawable.rajarani_icon, R.drawable.vishwaroopam_icon, R.drawable.i_head};

        int headImage[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head,
                R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

        for (int i = 0; i < 17; i++) {
            Movies temp = new Movies();
            temp.movieId = i;
            temp.movieName = movieName[i];
            temp.rating = ((int) (Math.random() * 10)) % 5 + 1;
            temp.year = 2000 + (int) (Math.random() * 10) % 15;
            temp.language = "Tamil";
            temp.casts = casts;
            temp.links = links;
            temp.iconImageId = iconImage[i];
            temp.headImageId = headImage[i];
            temp.iconImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, iconImage[i], 100, 100);
            temp.headImageBitmap = ImageOptimizer.getCorrespondingBitmap(resources, headImage[i], 200, 150);

            for (int j = 0; j < temp.links.size(); j++) {
                insertIntoLinksTable(sqLiteDatabase, temp.movieId, "Trailor", temp.links.get(j));
            }

            insertIntoMovieTable(sqLiteDatabase, temp.movieId, temp.movieName, temp.year, "10-10-2014", temp.language, 0, temp.iconImageId + "", temp.headImageId + "");
        }
    }

    public void initReviews(SQLiteDatabase sqLiteDatabase) {
        int sampleUserImage[] = {R.drawable.karthi,R.drawable.surya, R.drawable.trisha, R.drawable.rajini,R.drawable.rajini_2};
        String sampleUserId[] = {"Karthi","Surya","Trisha","Rajini","Bhaasha"};
        String sampleReviews[] = {"The Movie is nice, but the climax is okay","Thai herbs have increasingly gained public attention.Recently, there are a number of Thai herb websites. Each website" , "has similar information but quite different details. For example,some webpages do not provide information indicating which part" ,"of Thai herb can treat the specified symptom. In order to collect more complete Thai herb information, we have developed" ,"information extraction process to extract Thai herb information from multiple websites."};

        int i;
        for(i=0;i<30;i++){
            Reviews temp = new Reviews();
            temp.movieId = (i%13+1)+"";
            temp.likes = (int)(Math.random()*100);
            temp.unlikes = (int)(Math.random()*100);
            int x=((int)(Math.random()*100))%5;
            temp.userImage = sampleUserImage[x];
            temp.userId = sampleUserId[x];
            temp.review = sampleReviews[x];
            insertIntoReviewTable(sqLiteDatabase,i+1,temp.userId,(i%13)+1,temp.review,temp.likes,temp.unlikes);
        }

    }

    public void insertIntoLinksTable(SQLiteDatabase sqLiteDatabase, int movieId, String linkType, String linkURL) {
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("linkType", linkType);
        values.put("linkURL", linkURL);
        sqLiteDatabase.insert(LINKS_TABLE, null, values);
    }

    public void insertIntoMovieTable(SQLiteDatabase sqLiteDatabase, int movieId, String movieName, int year, String date, String language, int viewsCount, String iconImageURL, String headImageURL) {

        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("movieName", movieName);
        values.put("year", year);
        values.put("date", date);
        values.put("language", language);
        values.put("viewsCount", viewsCount);
        values.put("iconImageURL", iconImageURL);
        values.put("headImageURL", headImageURL);

        sqLiteDatabase.insert(MOVIE_TABLE, null, values);
    }

    public void insertIntoReviewTable(SQLiteDatabase sqLiteDatabase,int reviewId, String username, int movieId, String review, int likes, int unlikes){
        ContentValues values = new ContentValues();
        values.put("reviewId",reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("review", review);
        values.put("likes", likes);
        values.put("unlikes", unlikes);
        sqLiteDatabase.insert(REVIEW_TABLE, null, values);
    }

    public void reviewStatus(int reviewId, String username, int movieId, String status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reviewId",reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("status", status);
        sqLiteDatabase.insert(REVIEW_LIKE_TABLE, null, values);
    }

    public String getReviewStatus(int reviewId, int movieId, String username){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + REVIEW_LIKE_TABLE + " WHERE movieId=" + movieId + " and username='" + username +"' and reviewId=" + reviewId + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1){
            cursor.moveToFirst();
            return cursor.getString(3);
        }
        return null;
    }
    public boolean isRated(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + RATING_TABLE + " WHERE movieId'=" + movieId + "' and username='" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1)
            return true;
        return false;
    }

    public boolean isFollowed(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + FOLLOW_TABLE + " WHERE movieId'=" + movieId + "' and username='" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1)
            return true;
        return false;
    }

    public float getRating(int movieId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT AVG(rate) FROM " + RATING_TABLE + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return -1;
    }

    public void insertIntoRatingTable(int movieId, String username, float rate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("rate", rate);
        sqLiteDatabase.insert(RATING_TABLE, null, values);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MOVIE_TABLE + " ( " + "movieId int," + "movieName varchar," + "year int," + "date varchar," + "language varchar," + "viewsCount int," + "iconImageURL varchar," + "headImageURL varchar" + ")"); // make movie id as auto increment
        sqLiteDatabase.execSQL("CREATE TABLE " + CASTS_TABLE + " ( " + "movieId int," + "role varchar," + "personId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + ROLE_TABLE + " ( " + "personId int," + "name varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + LINKS_TABLE + " ( " + "movieId int," + "linkType varchar," + "linkURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + RATING_TABLE + " ( " + "movieId int," + "username varchar," + "rate float" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE + " ( " + "username varchar," + "name varchar," + "password varchar," + "emailId int," + "phone varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + FOLLOW_TABLE + " ( " + "username varchar," + "movieId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," + "review varchar," + "likes int,"+"unlikes int"+")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_LIKE_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," +" status varchar"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CASTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ROLE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LINKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FOLLOW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        onCreate(sqLiteDatabase);
    }
}

 /* */