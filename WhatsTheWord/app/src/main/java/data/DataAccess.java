package data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import models.Category;

public class DataAccess {
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

//    public Comment createComment(String comment) {
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
//        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
//                values);
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Comment newComment = cursorToComment(cursor);
//        cursor.close();
//        return newComment;
//    }
//
//    public void deleteComment(Comment comment) {
//        long id = comment.getId();
//        System.out.println("Comment deleted with id: " + id);
//        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//    }
//
//    public List<Comment> getAllComments() {
//        List<Comment> comments = new ArrayList<Comment>();
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Comment comment = cursorToComment(cursor);
//            comments.add(comment);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return comments;
//    }
//
//    private Comment cursorToComment(Cursor cursor) {
//        Comment comment = new Comment();
//        comment.setId(cursor.getLong(0));
//        comment.setComment(cursor.getString(1));
//        return comment;
//    }
}
