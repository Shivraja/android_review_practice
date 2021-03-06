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
public class DatabaseHandler extends SQLiteOpenHelper {

    static String DATABASE_NAME = "movies.db";
    static int DATABASE_VERSION = 26;
    static String MOVIE_TABLE = "movies";
    static String CASTS_TABLE = "casts";
    static String CELEBRITIES_TABLE = "celebrities";
    static String LINKS_TABLE = "links";
    static String RATING_TABLE = "rating";
    static String FOLLOW_TABLE = "follow";
    static String REVIEW_TABLE = "reviews";
    static String USER_TABLE = "user";
    static String REVIEW_LIKE_TABLE = "reviewlike";
    static String RECOMMENDATION_TABLE = "recommendations";

    Resources resources;

    public DatabaseHandler(Context context, Resources resources) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.resources = resources;
    }

    public void insertValues(SQLiteDatabase sqLiteDatabase) {
        Log.w("DATABASE","Initialization Begins");
        initMovies(sqLiteDatabase);
        initReviews(sqLiteDatabase);
        initUsers(sqLiteDatabase);
        initCelebrities(sqLiteDatabase);
        initRecommendations(sqLiteDatabase);
        Log.w("DATABASE", "Initialization Ends");
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
                R.drawable.a2, R.drawable.jannalooram_icon, R.drawable.naveenasaraswathisabatham_icon, R.drawable.naiyaandi_icon, R.drawable.samar_icon, R.drawable.vanayuddham_icon, R.drawable.chennaiyilorunaal_icon, R.drawable.vanakkamchennai_icon, R.drawable.kadal_icon, R.drawable.udhayamnh4_icon, R.drawable.a13, R.drawable.rajarani_icon, R.drawable.vishwaroopam_icon, R.drawable.i_head};

        int headImage[] = {R.drawable.yennaiarindhal_head, R.drawable.anegan_head, R.drawable.darling_head, R.drawable.jilla_head,
                R.drawable.head_4, R.drawable.jannalooram_head, R.drawable.naveenasaraswathisabatham_head, R.drawable.naiyaandi_head, R.drawable.samar_head, R.drawable.vanayuddham_head, R.drawable.chennaiyilorunaal_head, R.drawable.vanakkamchennai_head, R.drawable.kadal_head, R.drawable.udhayamnh4_head, R.drawable.arambam_head, R.drawable.rajarani_head, R.drawable.vishwaroopam_head, R.drawable.i_head};

        int englishIconImage[] = {R.drawable.eicon_1,R.drawable.eicon_2,R.drawable.eicon_3,R.drawable.eicon_4,R.drawable.eicon_5,R.drawable.eicon_6,R.drawable.eicon_7,R.drawable.eicon_8,R.drawable.eicon_9,R.drawable.eicon_10,R.drawable.eicon_11};

        int englishHeadImage[] ={R.drawable.ehead_1,R.drawable.ehead_2,R.drawable.ehead_3,R.drawable.ehead_4,R.drawable.ehead_5,R.drawable.ehead_6,R.drawable.ehead_7,R.drawable.ehead_8,R.drawable.ehead_9,R.drawable.ehead_10,R.drawable.ehead_11};

        String englishMovieName[] = {"Gamer","Sweet Punch","X-Men", "Hobbit", "The Bourne Legacy", "The Dark Knignt Arises", "Seventh Son", "Prince of Persia", "A Common Man", "Fast and Furious 7","Boys"};

        for (int i = 0; i < 17; i++) {
            MOVIE temp = new MOVIE();
            temp.movieId = i;
            temp.movieName = movieName[i];
            temp.rating = ((int) (Math.random() * 10)) % 5 + 1;
            temp.year = 2000 + (int) (Math.random() * 10) % 15;
            temp.language = "Tamil";
            temp.casts = getCasts(sqLiteDatabase, temp.movieId);
            temp.links = links;
            temp.iconImageId = iconImage[i];
            temp.headImageId = headImage[i];
            temp.description = "This is the Upcoming movie Released by Sun Network and its associatives. This film is contrubuted by Vijay Surya and his fellows. The film is panned to release for Pongal \n\nDirector :  Manirathnam\nMusic    :  Anirudh";
            for (int j = 0; j < 5; j++) {
                insertIntoCastsTable(sqLiteDatabase, temp.movieId, "None", ((j + i) % 7) + 1);
            }

            for (int j = 0; j < temp.links.size(); j++) {
                insertIntoLinksTable(sqLiteDatabase, temp.movieId, "Trailor", temp.links.get(j));
            }

            insertIntoMovieTable(sqLiteDatabase, temp.movieId, temp.movieName, temp.year, "10-10-2014", temp.language, temp.description, 0, temp.rating, temp.iconImageId + "", temp.headImageId + "");
        }

        for (int i = 0; i < 11; i++) {
            MOVIE temp = new MOVIE();
            temp.movieId = 100+i;
            temp.movieName = englishMovieName[i];
            temp.rating = ((int) (Math.random() * 10)) % 5 + 1;
            temp.year = 2000 + (int) (Math.random() * 10) % 15;
            temp.language = "English";
            temp.casts = getCasts(sqLiteDatabase, 1);  //1 for sample
            temp.links = links;
            temp.iconImageId = englishIconImage[i];
            temp.headImageId = englishHeadImage[i];
            temp.description = "This is the Upcoming movie Released by Star Network and its associatives. This film is contrubuted by Vijay Surya and his fellows. The film is panned to release for Pongal \n\nDirector :  JamesCameroon\nMusic    :  Anirudh";

            for (int j = 0; j < 5; j++) {
                insertIntoCastsTable(sqLiteDatabase, temp.movieId, "None", ((j + i) % 7) + 1);
            }

            for (int j = 0; j < temp.links.size(); j++) {
                insertIntoLinksTable(sqLiteDatabase, temp.movieId, "Trailor", temp.links.get(j));
            }

            insertIntoMovieTable(sqLiteDatabase, temp.movieId, temp.movieName, temp.year, "10-10-2014", temp.language, temp.description, 0, temp.rating, temp.iconImageId + "", temp.headImageId + "");
        }

    }

    public void initReviews(SQLiteDatabase sqLiteDatabase) {
        int sampleUserImage[] = {R.drawable.karthi, R.drawable.surya, R.drawable.trisha, R.drawable.rajini, R.drawable.rajini_2, R.drawable.kajal};
        String username[] ={"Rajini","Surya","Karthi","SuperStar","Singam","Kajal","Priya"};

        String sampleReviews[] = {"The MOVIE is nice, but the climax is okay", "Thai herbs have increasingly gained public attention.Recently, there are a number of Thai herb websites. Each website", "Has similar information but quite different details. For example,some webpages do not provide information indicating which part", "of Thai herb can treat the specified symptom. In order to collect more complete Thai herb information, we have developed", "information extraction process to extract Thai herb information from multiple websites.", "Return the class name of this object to be used for accessibility purposes. Subclasses should only override this if they are implementing something that should be seen as a completely new class of view when used by accessibility, unrelated to the class it is deriving from"};

        int i;
        for (i = 0; i < 50; i++) {
            REVIEW temp = new REVIEW();
            temp.movieId = (i % 17 + 1);
            temp.likes = (int) (Math.random() * 100);
            temp.unlikes = (int) (Math.random() * 100);
            int x = ((int) (Math.random() * 100)) % 6;
            temp.userImage = sampleUserImage[x];
            temp.userId = username[x];
            temp.review = sampleReviews[x];
            temp.time = i + "-10-2015";
            insertIntoReviewTable(sqLiteDatabase, i + 1, temp.userId, (i % 13) + 1, temp.review, temp.time, temp.likes, temp.unlikes);
        }
    }

    public void initUsers(SQLiteDatabase sqLiteDatabase) {
        String username[] ={"Rajini","Surya","Karthi","SuperStar","Singam","Kajal","Priya","Raja"};
        int userImage[] = {R.drawable.rajini, R.drawable.surya, R.drawable.karthi, R.drawable.rajini_2, R.drawable.surya_2, R.drawable.kajal, R.drawable.unknown, R.drawable.ajith};

        for(int i=0; i<8; i++){
            insertIntoUserTable(sqLiteDatabase, username[i], username[i], username[i], username[i] + "@gmail.com", "9788925328", userImage[i] + "");
        }

    }

    public void initRecommendations(SQLiteDatabase sqLiteDatabase){

        String username[] ={"Rajini","Surya","Karthi","SuperStar","Singam","Kajal","Priya","Raja"};
        for(int i=0; i<20;i++){
            int x = ((int) (Math.random() * 100)) % 8;
            insertIntoRecommendationTable(sqLiteDatabase, i, username[x], username[(x+i)%8]);
        }
    }

    public void insertIntoRecommendationTable(SQLiteDatabase sqLiteDatabase, int movieId, String fromUser, String toUser){
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("fromuser", fromUser);
        values.put("touser", toUser);
        sqLiteDatabase.insert(RECOMMENDATION_TABLE, null, values);
    }

    public void insertIntoUserTable(SQLiteDatabase sqLiteDatabase,String username, String name, String password, String emailId, String phone, String imageURL){
            ContentValues values = new ContentValues();
            values.put("username", username);
        values.put("name", name);
        values.put("password", password);
        values.put("emailId", emailId);
        values.put("phone", phone);
        values.put("imageURL", imageURL);
        sqLiteDatabase.insert(USER_TABLE,null,values);
    }

    public void initCelebrities(SQLiteDatabase sqLiteDatabase) {
        int imageId[] = {R.drawable.rajini, R.drawable.rajini_2, R.drawable.unknown, R.drawable.kajal, R.drawable.karthi, R.drawable.surya, R.drawable.surya_2};
        int personId[] = {1, 2, 3, 4, 5, 6, 7};
        String name[] = {"Rajini", "RajiniKanth", "Priya", "Kajal", "Karthi", "Surya", "Singam"};
        for (int i = 0; i < 7; i++) {
            insertIntoCelebritiesTable(sqLiteDatabase, personId[i], name[i], imageId[i] + "");
        }
    }

    public void insertIntoCastsTable(SQLiteDatabase sqLiteDatabase, int movieId, String role, int personId) {
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("role", role);
        values.put("personId", personId);
        sqLiteDatabase.insert(CASTS_TABLE, null, values);
    }

    public void insertIntoCelebritiesTable(SQLiteDatabase sqLiteDatabase, int personId, String personName, String imageURL) {
        ContentValues values = new ContentValues();
        values.put("personId", personId);
        values.put("name", personName);
        values.put("imageURL", imageURL);
        sqLiteDatabase.insert(CELEBRITIES_TABLE, null, values);
    }

    public void insertIntoLinksTable(SQLiteDatabase sqLiteDatabase, int movieId, String linkType, String linkURL) {
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("linkType", linkType);
        values.put("linkURL", linkURL);
        sqLiteDatabase.insert(LINKS_TABLE, null, values);
    }

    public CASTS[] getCasts(SQLiteDatabase sqLiteDatabase, int movieId) {
        String query = "SELECT movieId, name, role, imageURL FROM " + CASTS_TABLE + " , " + CELEBRITIES_TABLE + " WHERE movieId=" + movieId + " and " + CASTS_TABLE + ".personId = " + CELEBRITIES_TABLE + ".personId" + ";";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        CASTS[] casts = new CASTS[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                casts[index] = new CASTS();
                casts[index].movieId = cursor.getInt(0);
                casts[index].personName = cursor.getString(1);
                casts[index].role = cursor.getString(2);
                casts[index].imageId = Integer.parseInt(cursor.getString(3));
                index++;
            } while (cursor.moveToNext());
        }
        return casts;
    }

    public CASTS[] getCasts(int movieId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        sqLiteDatabase.execSQL("CREATE TABLE " + CASTS_TABLE + " ( " + "movieId int," + "role varchar," + "personId int" + ")");
//        sqLiteDatabase.execSQL("CREATE TABLE " + CELEBRITIES_TABLE + " ( " + "personId int," + "name varchar," + "imageURL varchar" + ")");
        String query = "SELECT movieId, name, role, imageURL FROM " + CASTS_TABLE + " , " + CELEBRITIES_TABLE + " WHERE movieId=" + movieId + " and " + CASTS_TABLE + ".personId = " + CELEBRITIES_TABLE + ".personId" + ";";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        CASTS[] casts = new CASTS[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                casts[index] = new CASTS();
                casts[index].movieId = cursor.getInt(0);
                casts[index].personName = cursor.getString(1);
                casts[index].role = cursor.getString(2);
                casts[index].imageId = Integer.parseInt(cursor.getString(3));
                index++;
            } while (cursor.moveToNext());
        }
        return casts;
    }

    public void insertIntoMovieTable(SQLiteDatabase sqLiteDatabase, int movieId, String movieName, int year, String date, String language, String description, int viewcount, float rate, String iconImageURL, String headImageURL) {

        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("movieName", movieName);
        values.put("year", year);
        values.put("date", date);
        values.put("language", language);
        values.put("viewcount", viewcount);
        values.put("rate", rate);
        values.put("iconImageURL", iconImageURL);
        values.put("headImageURL", headImageURL);
        values.put("description", description);
        sqLiteDatabase.insert(MOVIE_TABLE, null, values);
    }

    public void insertIntoReviewTable(SQLiteDatabase sqLiteDatabase, int reviewId, String username, int movieId, String review, String time, int likes, int unlikes) {
        ContentValues values = new ContentValues();
        values.put("reviewId", reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("REVIEW", review);
        values.put("time", time);
        values.put("likes", likes);
        values.put("unlikes", unlikes);
        sqLiteDatabase.insert(REVIEW_TABLE, null, values);
    }

    /* public MOVIE[] getMovies() {
         SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
         String query = "SELECT * FROM " + MOVIE_TABLE + ";";
         Cursor cursor = sqLiteDatabase.rawQuery(query, null);
         MOVIE[] movies = new MOVIE[cursor.getCount()];
         int index = 0;
         if (cursor.moveToFirst()) {
             do {
                 movies[index] = new MOVIE();
                 movies[index].movieId = cursor.getInt(0);
                 movies[index].movieName = cursor.getString(1);
                 movies[index].year = cursor.getInt(2);
                 movies[index].date = cursor.getString(3);
                 movies[index].language = cursor.getString(4);
                 movies[index].viewCount = cursor.getInt(5);
                 movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                 movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                 movies[index].rating = getMovieRating(movies[index].movieId);
                 movies[index].description = cursor.getString(9);
                 index++;
             } while (cursor.moveToNext());
         }
         return movies;
     }
 */
    public MOVIE getMovie(int movieId) {
        Log.w("DATABASE", "getMovie");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " WHERE movieId = " + movieId + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE MOVIE = new MOVIE();
        if (cursor.moveToFirst()) {
            MOVIE.movieId = cursor.getInt(0);
            MOVIE.movieName = cursor.getString(1);
            MOVIE.year = cursor.getInt(2);
            MOVIE.date = cursor.getString(3);
            MOVIE.language = cursor.getString(4);
            MOVIE.viewCount = cursor.getInt(5);
            MOVIE.iconImageId = Integer.parseInt(cursor.getString(7));
            MOVIE.headImageId = Integer.parseInt(cursor.getString(8));
            MOVIE.rating = getMovieRating(MOVIE.movieId);
            MOVIE.description = cursor.getString(9);
        }
        return MOVIE;
    }

    public MOVIE[] getTopPopularMovies(String language) {
        Log.w("DATABASE", "getTopPopularMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY viewcount DESC LIMIT 5" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public MOVIE[] getPopularMovies(String language) {
        Log.w("DATABASE", "getPopularMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY viewcount DESC" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public MOVIE[] getTopRatedMovies(String language) {
        Log.w("DATABASE", "getTopRatedMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY rate DESC LIMIT 5" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public MOVIE[] getRatedMovies(String language) {
        Log.w("DATABASE", "getRatedMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY rate DESC" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public MOVIE[] getTopRecentMovies(String language) {
        Log.w("DATABASE", "getTopRecentMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY date DESC LIMIT 5" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public void addMovieReview(int movieId, String username, String review) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        insertIntoReviewTable(sqLiteDatabase, 21, username, movieId, review, "10-10-2015", 0, 0);
        sqLiteDatabase.close();
    }

    public void updateMovieReview(int movieId, String username, String review) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "UPDATE " + REVIEW_TABLE + " SET review='" + review + "' WHERE movieId=" + movieId + " and username='" + username + "'" + ";";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public void removeMovieReview(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "DELETE * FROM " + REVIEW_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "'" + ";";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public MOVIE[] getRecentMovies(String language) {
        Log.w("DATABASE", "getRecentMovies");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " where language='" + language + "' ORDER BY date DESC" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public MOVIE[] getFollowedMovies(String username) {
        Log.w("DATABASE", "getFollowedMovies");

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MOVIE_TABLE + " WHERE movieId IN " + "(" + "SELECT movieId FROM " + FOLLOW_TABLE + " WHERE username='" + username + "'" + " )" + " ORDER BY date DESC" + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MOVIE[] movies = new MOVIE[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                movies[index] = new MOVIE();
                movies[index].movieId = cursor.getInt(0);
                movies[index].movieName = cursor.getString(1);
                movies[index].year = cursor.getInt(2);
                movies[index].date = cursor.getString(3);
                movies[index].language = cursor.getString(4);
                movies[index].viewCount = cursor.getInt(5);
                movies[index].iconImageId = Integer.parseInt(cursor.getString(7));
                movies[index].headImageId = Integer.parseInt(cursor.getString(8));
                movies[index].rating = getMovieRating(movies[index].movieId);
                movies[index].description = cursor.getString(9);
                index++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return movies;
    }

    public LINKS[] getLinks(int movieId) {
        Log.w("DATABASE", "getLinks");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + LINKS_TABLE + " WHERE movieId=" + movieId + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        LINKS[] links = new LINKS[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                links[index] = new LINKS();
                links[index].movieId = cursor.getInt(0);
                links[index].linkType = cursor.getString(1);
                links[index].linkURL = cursor.getString(2);
                index++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return links;
    }

    public REVIEW[] getReviews(int movieId) {
        Log.w("DATABASE", "getReviews");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + REVIEW_TABLE + " WHERE movieId= " + movieId + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        REVIEW[] reviews = new REVIEW[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                reviews[index] = new REVIEW();
                reviews[index].reviewId = cursor.getInt(0);
                reviews[index].userId = cursor.getString(1);
                reviews[index].movieId = cursor.getInt(2);
                reviews[index].review = cursor.getString(3);
                reviews[index].likes = cursor.getInt(4);
                reviews[index].unlikes = cursor.getInt(5);
                index++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return reviews;
    }

    public REVIEW getReview(int movieId, String username) {
        Log.w("DATABASE", "getReviews");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + REVIEW_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "' " + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        REVIEW review = null;
        if (cursor.getCount() < 1)
            return null;
        if (cursor.moveToFirst()) {
            review = new REVIEW();
            review.reviewId = cursor.getInt(0);
            review.userId = cursor.getString(1);
            review.movieId = cursor.getInt(2);
            review.review = cursor.getString(3);
            review.likes = cursor.getInt(4);
            review.unlikes = cursor.getInt(5);
        }
        sqLiteDatabase.close();
        return review;
    }

    public void incrementViewCount(int movieId) {
        Log.w("DATABASE", "increment view count " + movieId);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE " + MOVIE_TABLE + " SET viewcount=viewcount+1 where movieId=" + movieId + ";");
        sqLiteDatabase.close();
    }

    public void addReviewStatus(int reviewId, String username, int movieId, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reviewId", reviewId);
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("status", status);
        sqLiteDatabase.insert(REVIEW_LIKE_TABLE, null, values);
        sqLiteDatabase.close();
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
        if (cursor.getCount() >= 1) {
            sqLiteDatabase.close();
            return true;
        }
        sqLiteDatabase.close();
        return false;
    }

    public boolean isFollowed(int movieId, String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + FOLLOW_TABLE + " WHERE movieId=" + movieId + " and username='" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1) {
            sqLiteDatabase.close();
            return true;
        }
        sqLiteDatabase.close();
        return false;
    }

    public void addFollow(int movieId, String username) {
        CONSTANTS.FAVOURITE_ALTERED = true;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("username", username);
        sqLiteDatabase.insert(FOLLOW_TABLE, null, values);
        sqLiteDatabase.close();
    }

    public int[] getCelebritiesImageId() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT imageURL FROM " + CELEBRITIES_TABLE + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int imageId[] = new int[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                imageId[index] = Integer.parseInt(cursor.getString(0));
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return imageId;
    }

    public float getMovieRating(int movieId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT AVG(rate) FROM " + RATING_TABLE + " WHERE movieId=" + movieId + " ;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return 0;
    }

    public void addMovieRating(int movieId, String username, float rate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movieId", movieId);
        values.put("username", username);
        values.put("rate", rate);
        sqLiteDatabase.insert(RATING_TABLE, null, values);
        sqLiteDatabase.close();
    }

    public void removeFollow(int movieId, String username) {
        CONSTANTS.FAVOURITE_ALTERED = true;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(FOLLOW_TABLE, " movieId=" + movieId + " and username='" + username + "' ", null);
        sqLiteDatabase.close();
    }

    public void recommendMovie(int movieId, String fromUser, String toUser){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        insertIntoRecommendationTable(sqLiteDatabase,movieId,fromUser,toUser);
        sqLiteDatabase.close();
    }

    public RECOMMEND[] getRecommendations(String username){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT movieId, fromuser FROM "+ RECOMMENDATION_TABLE +" WHERE touser='"+ username +"'"+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        RECOMMEND[] recommends = new RECOMMEND[cursor.getCount()];
        int index = 0;
        if(cursor.moveToFirst()){
            do{
                recommends[index] = new RECOMMEND();
                recommends[index].movieId = cursor.getInt(0);
                recommends[index].username = cursor.getString(1);
                index++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return recommends;
    }

    public void clearRecommendations(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "DELETE FROM "+RECOMMENDATION_TABLE+" WHERE touser='"+username+"';";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MOVIE_TABLE + " ( " + "movieId int," + "movieName varchar," + "year int," + "date varchar," + "language varchar," + "viewcount int," + "rate float," + "iconImageURL varchar," + "headImageURL varchar," + "description varchar(1000)" + ")"); // make movie id as auto increment
        sqLiteDatabase.execSQL("CREATE TABLE " + CASTS_TABLE + " ( " + "movieId int," + "role varchar," + "personId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + CELEBRITIES_TABLE + " ( " + "personId int," + "name varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + LINKS_TABLE + " ( " + "movieId int," + "linkType varchar," + "linkURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + RATING_TABLE + " ( " + "movieId int," + "username varchar," + "rate float" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE + " ( " + "username varchar," + "name varchar," + "password varchar," + "emailId int," + "phone varchar," + "imageURL varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + FOLLOW_TABLE + " ( " + "username varchar," + "movieId int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," + "REVIEW varchar," + " time varchar," + "likes int," + "unlikes int" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + REVIEW_LIKE_TABLE + " ( " + "reviewId int," + "username varchar," + "movieId int," + " status varchar" + ")");
        sqLiteDatabase.execSQL("CREATE TABLE " + RECOMMENDATION_TABLE + " ( " + "movieId int," + "fromuser varchar," + "touser varchar" +  ")");

        Log.w("DATABASE", "Tables created");
        insertValues(sqLiteDatabase);
        Log.w("DATABASE", "Values Inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CASTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CELEBRITIES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LINKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FOLLOW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REVIEW_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RECOMMENDATION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
