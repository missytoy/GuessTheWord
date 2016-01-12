package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import helpers.Utils;
import helpers.WordsAndCategoriesConstants;
import models.Category;
import models.Word;

public class DatabaseHelper extends SQLiteOpenHelper{
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
            + KEY_GAME_PLAYED_ON + " DATETIME, "
            + KEY_GAME_LOCATION + " TEXT, "
            + KEY_GAME_CATEGORYID + " INTEGER,FOREIGN KEY(" + KEY_GAME_CATEGORYID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")"
            + ")";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_NAME + " TEXT,"
            + KEY_CATEGORY_IMAGEID + " INTEGER"
            + ")";

    // Word table create statement
    private static final String CREATE_TABLE_WORD= "CREATE TABLE " + TABLE_WORD
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_WORD_CONTENT + " TEXT UNIQUE, "
            + KEY_WORD_CATEGORYID + " INTEGER,FOREIGN KEY(" + KEY_WORD_CATEGORYID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")"
            + ")";

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
        seedDatabase(db);
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

    private void seedDatabase(SQLiteDatabase db){
        for (int i = 0; i < WordsAndCategoriesConstants.CATEGORY_NAMES.length; i++) {
            Category newCategory = new Category();
            newCategory.setName(WordsAndCategoriesConstants.CATEGORY_NAMES[i]);
            // when we load categories in the view we will use this to set their background image
            newCategory.setImageResourceId(Utils.getDrawableId(WordsAndCategoriesConstants.CATEGORY_IMAGE_NAMES[i]));

            int categoryId = (int) createCategory(newCategory, db);

            String[] catWords = WordsAndCategoriesConstants.WORDS_BY_CATEGORYNAME.get(WordsAndCategoriesConstants.CATEGORY_NAMES[i]);

            for (int j = 0; j < catWords.length; j++) {
                Word wordModelToAdd = new Word();
                wordModelToAdd.setContent(catWords[j]);
                wordModelToAdd.setCategoryId(categoryId);

                this.createWord(wordModelToAdd, db);
            }
        }
    }

    // Create category
    private long createCategory(Category category, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CATEGORY_NAME, category.getName());
        values.put(DatabaseHelper.KEY_CATEGORY_IMAGEID, category.getImageResourceId());

        // insert row
        long category_id = database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);

        return category_id;
    }

    // Create word
    private Long createWord(Word wordModelToAdd, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_WORD_CONTENT, wordModelToAdd.getContent());
        values.put(DatabaseHelper.KEY_WORD_CATEGORYID, wordModelToAdd.getCategoryId());

        // insert row
        Long word_id = database.insert(DatabaseHelper.TABLE_WORD, null, values);

        return word_id;
    }
}
