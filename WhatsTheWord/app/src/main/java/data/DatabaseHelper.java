package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import helpers.Utils;
import models.Category;

public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String[] CATEGORY_NAMES = new String[]{"Objects", "Songs", "Animals", "Bg Stars",
                                                                "Cars", "Food", "Movies", "Bg Songs", "Stars"};

    private final static String[] CATEGORY_IMAGE_NAMES = new String[]{"object_category", "music_category", "animal_category",
                                                                      "bgstars_category", "cars_category", "food_category",
                                                                      "movie_category", "musicbg_category", "stars_category"};
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "whatsTheWordDb";

    // Table Names
    public static final String TABLE_PLAYER = "players";
    public static final String TABLE_GAME = "games";
    public static final String TABLE_WORD = "words";
    public static final String TABLE_CATEGORY = "categories";

    // Common column names
    public static final String KEY_ID = "id";

    // PLAYER Table - column nmaes
    public static final String KEY_PLAYER_NAME = "name";
    public static final String KEY_PLAYER_SCORE = "score";
    public static final String KEY_PLAYER_GAMEID = "game_id";

    // GAME Table - column names
    public static final String KEY_GAME_NAME = "game_name";
    public static final String KEY_GAME_PLAYED_ON = "played_on";
    public static final String KEY_GAME_LOCATION = "location";
    public static final String KEY_GAME_CATEGORYID = "category_id";

    // WORD Table - column names
    public static final String KEY_WORD_CONTENT = "content";
    public static final String KEY_WORD_CATEGORYID = "category_id";

    // CATEGORY Table - column names
    public static final String KEY_CATEGORY_NAME = "name";
    public static final String KEY_CATEGORY_IMAGEID = "image_id";

    // Table Create Statements
    // Player table create statement
    private static final String CREATE_TABLE_PLAYER = "CREATE TABLE " + TABLE_PLAYER
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLAYER_NAME + " TEXT,"
            + KEY_PLAYER_SCORE + " INTEGER,"
            + KEY_PLAYER_GAMEID + " INTEGER,FOREIGN KEY(" + KEY_PLAYER_GAMEID + ") REFERENCES " + TABLE_GAME + "(" + KEY_ID + ")"
            + ")";

    // Game table create statement
    private static final String CREATE_TABLE_GAME = "CREATE TABLE " + TABLE_GAME
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_GAME_NAME + " TEXT,"
            + KEY_GAME_PLAYED_ON + " DATETIME"
            + KEY_GAME_CATEGORYID + " INTEGER,FOREIGN KEY(" + KEY_GAME_CATEGORYID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")"
            + KEY_GAME_LOCATION + "TEXT"
            + ")";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_NAME + " TEXT"
            + KEY_CATEGORY_IMAGEID + "INTEGER"
            + ")";

    // Word table create statement
    private static final String CREATE_TABLE_WORD= "CREATE TABLE " + TABLE_WORD
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_WORD_CONTENT + " TEXT,CONSTRAINT content_UNIQUE UNIQUE (" + KEY_WORD_CONTENT + ")"
            + KEY_WORD_CATEGORYID + " INTEGER,FOREIGN KEY(" + KEY_WORD_CATEGORYID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")"
            + ")";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_GAME);
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_WORD);
        seedDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);

        // create new tables
        onCreate(db);
    }

    public long createCategory(Category category) {

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category.getName());
        values.put(KEY_CATEGORY_IMAGEID, category.getImageResourceId());

        // insert row
        long category_id = db.insert(TABLE_CATEGORY, null, values);

        return category_id;
    }


    private void seedDatabase(){
        db = this.getWritableDatabase();

        for (int i = 0; i < CATEGORY_NAMES.length; i++) {
            Category newCategory = new Category();
            newCategory.setName(CATEGORY_NAMES[i]);
            // when we load categories in the view we will use this to set their background image
            newCategory.setImageResourceId(Utils.getDrawableId(CATEGORY_IMAGE_NAMES[i]));

            createCategory(newCategory);
        }

        db.close();
    }
}
