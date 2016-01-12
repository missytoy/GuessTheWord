package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import models.Category;
import models.Game;
import models.Player;
import models.Word;

public class DataAccess implements Serializable{
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataAccess(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Get all Categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_CATEGORY;

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(c.getInt((c.getColumnIndex(DatabaseHelper.KEY_ID))));
                cat.setName(c.getString(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY_NAME)));
                cat.setImageResourceId(c.getInt(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY_IMAGEID)));

                // adding to category list
                categories.add(cat);
            } while (c.moveToNext());
        }

        c.close();
        return categories;
    }

    // Create word
    public Long createWord(Word wordModelToAdd){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_WORD_CONTENT, wordModelToAdd.getContent());
        values.put(DatabaseHelper.KEY_WORD_CATEGORYID, wordModelToAdd.getCategoryId());

        // insert row
        Long word_id = database.insert(DatabaseHelper.TABLE_WORD, null, values);

        return word_id;
    }

    // Get content of all words
    public List<String> getAllWordsAsContent(){
        List<String> words = new ArrayList<String>();
        String selectQuery = "SELECT " + DatabaseHelper.KEY_WORD_CONTENT + " FROM " + DatabaseHelper.TABLE_WORD;

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String word = c.getString(c.getColumnIndex(DatabaseHelper.KEY_WORD_CONTENT));

                // adding to category list
                words.add(word);
            } while (c.moveToNext());
        }

        c.close();
        return words;
    }

    // Get content of all words by category
    public AbstractSet<String> getAllWordsContentByCategory(int currentCategoryId){
        AbstractSet<String> words = new HashSet<String>();
        String selectQuery = "SELECT " + DatabaseHelper.KEY_WORD_CONTENT + " FROM " + DatabaseHelper.TABLE_WORD
                             + " WHERE " + DatabaseHelper.KEY_WORD_CATEGORYID + " = " + currentCategoryId;

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String word = c.getString(c.getColumnIndex(DatabaseHelper.KEY_WORD_CONTENT));

                // adding to category list
                words.add(word);
            } while (c.moveToNext());
        }

        c.close();
        return words;
    }

    // Create game
    public long createGame(Game gameModel){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_GAME_CATEGORYID, gameModel.getCategoryId());
        values.put(DatabaseHelper.KEY_GAME_LOCATION, gameModel.getLocation());
        values.put(DatabaseHelper.KEY_GAME_PLAYED_ON, persistDate(gameModel.getPlayedOn()));

        // insert row
        Long game_id = database.insert(DatabaseHelper.TABLE_GAME, null, values);

        return 5L;
    }

    // Get of all games
    public List<Game> getAllGames(){
        List<Game> games = new ArrayList<Game>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_GAME
                + " ORDER BY " + DatabaseHelper.KEY_GAME_PLAYED_ON + " DESC";

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Game game = new Game();
                game.setId(c.getInt(c.getColumnIndex(DatabaseHelper.KEY_ID)));
                game.setCategoryId(c.getInt((c.getColumnIndex(DatabaseHelper.KEY_GAME_CATEGORYID))));
                game.setLocation(c.getString(c.getColumnIndex(DatabaseHelper.KEY_GAME_LOCATION)));
                game.setPlayedOn(loadDate(c, c.getColumnIndex(DatabaseHelper.KEY_GAME_PLAYED_ON)));

                // adding to category list
                games.add(game);
            } while (c.moveToNext());
        }

        c.close();
        return games;
    }

    // Create player
    public long createPlayer(Player playerModel){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PLAYER_NAME, playerModel.getName());
        values.put(DatabaseHelper.KEY_PLAYER_SCORE, playerModel.getScore());
        values.put(DatabaseHelper.KEY_PLAYER_GAMEID, playerModel.getGameId());

        // insert row
        Long player_id = database.insert(DatabaseHelper.TABLE_PLAYER, null, values);

        return player_id;
    }

    // Get all player names
    public AbstractSet<String> getAllPlayerNames(){
        AbstractSet<String> names = new HashSet<String>();
        String selectQuery = "SELECT " + DatabaseHelper.KEY_PLAYER_NAME + " FROM " + DatabaseHelper.TABLE_PLAYER;

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String name = c.getString(c.getColumnIndex(DatabaseHelper.KEY_PLAYER_NAME));

                names.add(name);
            } while (c.moveToNext());
        }

        c.close();
        return names;
    }

    private static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    private static Date loadDate(Cursor cursor, int index) {
        if (cursor.isNull(index)) {
            return null;
        }
        return new Date(cursor.getLong(index));
    }
}
