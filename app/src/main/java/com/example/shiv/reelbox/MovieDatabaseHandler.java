package com.example.shiv.reelbox;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiv on 07-Nov-15.
 */
public class MovieDatabaseHandler extends SQLiteOpenHelper {

    Resources resources;
    static String DATABASE_NAME = "movies.db";
    static int DATABASE_VERSION = 4;
    static String MOVIE_TABLE = "movies";
    static String CASTS_TABLE = "casts";
    static String PERSONALITIES_TABLE = "personalities";
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
        initUsers(sqLiteDatabase);
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
            Movie temp = new Movie();
            temp.movieId = i;
            temp.movieName = movieName[i];
            temp.rating = ((int) (Math.random() * 10)) % 5 + 1;
            temp.year = 2000 + (int) (Math.random() * 10) % 15;
            temp.language = "Tamil";
            temp.casts = casts;
            temp.links = links;
            temp.iconImageId = iconImage[i];
            temp.headImageId = headImage[i];

            for (int j = 0; j < temp.links.size(); j++) {
                insertIntoLinksTable(sqLiteDatabase, temp.movieId, "Trailor", temp.links.get(j));
            }

            insertIntoMovieTable(sqLiteDatabase, temp.movieId, temp.movieName, temp.year, "10-10-2014", temp.language, 0, temp.rating, temp.iconImageId + "", temp.headImageId + "");
        }
    }

    public void initReviews(SQLiteDatabase sqLiteDatabase) {
        int sampleUserImage[] = {R.drawable.karthi, R.drawable.surya, R.drawable.trisha, R.drawable.rajini, R.drawable.rajini_2};
        String sampleUserId[] = {"Karthi", "Surya", "Trisha", "Rajini", "Bhaasha"};
        String sampleReviews[] = {"The Movie is nice, but the climax is okay", "Thai herbs have increasingly gained public attention.Recently, there are a number of Thai herb websites. Each website", "has similar information but quite different details. For example,some webpages do not provide information indicating which part", "of Thai herb can treat the specified symptom. In order to collect more complete Thai herb information, we have developed", "information extraction process to extract Thai herb information from multiple websites."};

        int i;
        for (i = 0; i < 30; i++) {
            Review temp = new Review();
            temp.movieId = (i % 13 + 1);
            temp.likes = (int) (Math.random() * 100);
            temp.unlikes = (int) (Math.random() * 100);
            int x = ((int) (Math.random() * 100)) % 5;
            temp.userImage = sampleUserImage[x];
            temp.userId = sampleUserId[x];
            temp.review = sampleReviews[x];
            temp.time = i+"-10-2015";
            insertIntoReviewTable(sqLiteDatabase, i + 1, temp.userId, (i % 13) + 1, temp.review, temp.time, temp.likes, temp.unlikes);
        }

    }

    public void initUsers(SQLiteDatabase sqLiteDatabase) {

    }

    public void insertIntoLinksTable(SQLiteDatabase sqLiteDatabase, int movieId, String linkType, String linkURL) {
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("linkType", linkType);
        values.put("linkURL", linkURL);
        sqLiteDatabase.insert(LINKS_TABLE, null, values);
    }

    public void insertIntoMovieTable(SQLiteDatabase sqLiteDatabase, int movieId, String movieName, int year, String date, String language, int viewcount, float rate, String iconImageURL, String headImageURL) {

        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("movieName", movieName);
        values.put("year", year);
        values.put("date", date);
        values.put("language", language);
        values.put("viewcount", viewcount);
        values.put("rate",rate);
        values.put("iconImageURL", iconImageURL);
        values.put("headImageURL", headImageURL);
        sqLiteDatabase.insert(MOVIE_TABLE, null, values);
    }

    public void insertIntoReviewTable(SQLiteDatabase sqLiteDatabase, int reviewId, String username, int movieId, String review, String time, int likes, int unlikes) {
        ContentValues values = new ContentValues();
        values.put("reviewId", reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("review", review);
        values.put("time",time);
        values.put("likes", likes);
        values.put("unlikes", unlikes);
        sqLiteDatabase.insert(REVIEW_TABLE, null, values);
    }

    public Movie[] getMovies() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie getMovie(int movieId){
        Log.w("DATABASE","getMovie");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE +" WHERE movieId = "+movieId+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie movie = new Movie();
        if (cursor.moveToFirst()) {
                movie.movieId = cursor.getInt(0);
                movie.movieName = cursor.getString(1);
                movie.year = cursor.getInt(2);
                movie.date = cursor.getString(3);
                movie.language = cursor.getString(4);
                movie.viewCount = cursor.getInt(5);
                movie.iconImageId = Integer.parseInt(cursor.getString(7));
                movie.headImageId = Integer.parseInt(cursor.getString(8));
                movie.rating = getMovieRating(movie.movieId);
        }
        return movie;
    }

    public Movie[] getTopPopularMovies(String language) {
        Log.w("DATABASE","getTopPopularMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE +" where language='"+language+"' ORDER BY viewcount DESC LIMIT 5" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie[] getPopularMovies(String language) {
        Log.w("DATABASE","getPopularMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE +" where language='"+language+ "' ORDER BY viewcount DESC" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie[] getTopRatedMovies(String language) {
        Log.w("DATABASE","getTopRatedMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+ MOVIE_TABLE +" where language='"+language+ "' ORDER BY rate DESC LIMIT 5" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie[] getRatedMovies(String language) {
        Log.w("DATABASE","getRatedMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+ MOVIE_TABLE +" where language='"+language+ "' ORDER BY rate DESC" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie[] getTopRecentMovies(String language) {
        Log.w("DATABASE","getTopRecentMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+ MOVIE_TABLE +" where language='"+language+ "' ORDER BY date DESC LIMIT 5" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Movie[] getRecentMovies(String language) {
        Log.w("DATABASE","getRecentMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE +" where language='"+language+ "' ORDER BY date DESC" +";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Movie[] movies = new Movie[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new Movie();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public Links[] getLinks(int movieId){
        Log.w("DATABASE","getLinks");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + LINKS_TABLE +" WHERE movieId="+movieId+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Links[] links = new Links[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                links[index] = new Links();
                links[index].movieId = cursor.getInt(0);
                links[index].linkType = cursor.getString(1);
                links[index].linkURL = cursor.getString(2);
                index++;
            } while (cursor.moveToNext());
        }
        return links;
    }

    public Review[] getReviews(int movieId){
        Log.w("DATABASE","getReviews");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + REVIEW_TABLE +" WHERE movieId= "+movieId+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Review[] reviews = new Review[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                reviews[index] = new Review();
                reviews[index].reviewId = cursor.getInt(0);
                reviews[index].userId = cursor.getString(1);
                reviews[index].movieId = cursor.getInt(2);
                reviews[index].review = cursor.getString(3);
                reviews[index].likes = cursor.getInt(4);
                reviews[index].unlikes = cursor.getInt(5);
                index++;
            } while (cursor.moveToNext());
        }
        return reviews;
    }


    public void addReviewStatus(int reviewId, String username, int movieId, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reviewId", reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("status", status);
        sqLiteDatabase.insert(REVIEW_LIKE_TABLE, null, values);
    }

    public String getReviewStatus(int reviewId, int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + REVIEW_LIKE_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "' and reviewId=" + reviewId + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            return cursor.getString(3);
        }
        return null;
    }

    public boolean isRated(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + RATING_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1)
            return true;
        return false;
    }

    public boolean isFollowed(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + FOLLOW_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1)
            return true;
        return false;
    }

    public void addFollow(int movieId, String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("username", username);
        sqLiteDatabase.insert(FOLLOW_TABLE, null, values);
    }

    public float getMovieRating(int movieId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT AVG(rate) FROM " + RATING_TABLE + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return -1;
    }

    public void addMovieRating(int movieId, String username, float rate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("rate", rate);
        sqLiteDatabase.insert(RATING_TABLE, null, values);
    }

    public void removeFollow(int movieId, String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(FOLLOW_TABLE,"movieId="+movieId,null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MOVIE_TABLE + " ( " + "movieId int," + "movieName varchar," + "year int," + "date varchar," + "language varchar," + "viewcount int," +"rate float,"+ "iconImageURL varchar," + "headImageURL varchar" + ")"); // make movie id as auto increment
        sqLiteDatabase.execSQL("CREATE TABLE " + CASTS_TABLE + " ( " + "movieId int," + "role varchar," + "personId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + PERSONALITIES_TABLE + " ( " + "personId int," + "name varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + LINKS_TABLE + " ( " + "movieId int," + "linkType varchar," + "linkURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + RATING_TABLE + " ( " + "movieId int," + "username varchar," + "rate float" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE + " ( " + "username varchar," + "name varchar," + "password varchar," + "emailId int," + "phone varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + FOLLOW_TABLE + " ( " + "username varchar," + "movieId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," + "review varchar," +" time varchar," +"likes int," + "unlikes int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_LIKE_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," + " status varchar" + ")");
        Log.w("DATABASE", "Tables created");
        insertValues(sqLiteDatabase);
        Log.w("DATABASE", "Values Inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CASTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERSONALITIES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LINKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FOLLOW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_LIKE_TABLE);
        onCreate(sqLiteDatabase);
    }
}

 /* */