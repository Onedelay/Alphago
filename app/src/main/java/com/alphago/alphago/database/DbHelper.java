package com.alphago.alphago.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alphago.alphago.database.CardBookContract.*;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su_me on 2018-02-25.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CARD_BOOK.db";

    private static final String SQL_CREATE_CARD_BOOK =
            "CREATE TABLE " + CardBookEntry.TABLE_NAME + " (" +
                    CardBookEntry._ID + " INTEGER PRIMARY KEY," +
                    CardBookEntry.COLUMN_NAME_CATEGORY + " INTERGER ," +
                    CardBookEntry.COLUMN_NAME_LABEL + " TEXT ," +
                    CardBookEntry.COLUMN_NAME_PATH + " TEXT ,)"+
                    CardBookEntry.COLUMN_NAME_COLLECT+" INTEGER";

    private static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryEntry.COLUMN_NAME_LABEL + " TEXT ," +
                    CategoryEntry.COLUMN_NAME_PATH + " TEXT)";

    // Default images
    private static final String[] CATEGORY_LIST = {"animal", "fruit", "furniture", "vegetable"};
    private static final String IMAGE_LIST_ANIMAL[] = {"dog", "cat", "lion"};
    private static final String IMAGE_LIST_FRUIT[] = {"apple", "banana", "strawberry", "lemon"};
    private static final String IMAGE_LIST_FURNITURE[] = {"bed", "chair", "desk"};
    private static final String IMAGE_LIST_VEGETABLE[] = {"tomato", "bean", "cucumber", "potato", "pumpkin"};

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CARD_BOOK);
        db.execSQL(SQL_CREATE_CATEGORY);

        for (String category : CATEGORY_LIST) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME_LABEL, category);
            db.insert(CategoryEntry.TABLE_NAME, null, values); // return row ID (long)
        }

        for (String label : IMAGE_LIST_ANIMAL) {
            ContentValues values = new ContentValues();
            values.put(CardBookEntry.COLUMN_NAME_LABEL, label);
            values.put(CardBookEntry.COLUMN_NAME_CATEGORY, 1);
            db.insert(CardBookEntry.TABLE_NAME, null, values);
        }

        for (String label : IMAGE_LIST_FRUIT) {
            ContentValues values = new ContentValues();
            values.put(CardBookEntry.COLUMN_NAME_LABEL, label);
            values.put(CardBookEntry.COLUMN_NAME_CATEGORY, 2);
            db.insert(CardBookEntry.TABLE_NAME, null, values);
        }

        for (String label : IMAGE_LIST_FURNITURE) {
            ContentValues values = new ContentValues();
            values.put(CardBookEntry.COLUMN_NAME_LABEL, label);
            values.put(CardBookEntry.COLUMN_NAME_CATEGORY, 3);
            db.insert(CardBookEntry.TABLE_NAME, null, values);
        }

        for (String label : IMAGE_LIST_VEGETABLE) {
            ContentValues values = new ContentValues();
            values.put(CardBookEntry.COLUMN_NAME_LABEL, label);
            values.put(CardBookEntry.COLUMN_NAME_CATEGORY, 4);
            db.insert(CardBookEntry.TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Category> categorySelect() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        Cursor c = db.query(CategoryEntry.TABLE_NAME, projection, null, null, null, null, null);
        List<Category> categoryList = new ArrayList<>();

        while (c.moveToNext()) {
            long categoryId = c.getLong(c.getColumnIndexOrThrow(CategoryEntry._ID));
            String category = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_LABEL));
            categoryList.add(new Category(categoryId, category, null));
        }
        c.close();
        return categoryList;
    }

    public List<CardBook> labelSelect(long categoryId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        String selection = CardBookEntry.COLUMN_NAME_CATEGORY + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};

        Cursor c = db.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        List<CardBook> labelList = new ArrayList<>();

        while (c.moveToNext()) {
            long labelId = c.getLong(c.getColumnIndexOrThrow(CardBookEntry._ID));
            String label = c.getString(c.getColumnIndexOrThrow(CardBookEntry.COLUMN_NAME_LABEL));
            String path = c.getString(c.getColumnIndexOrThrow(CardBookEntry.COLUMN_NAME_PATH));
            labelList.add(new CardBook(labelId, label, categoryId, String.valueOf(categoryId), path));
        }
        c.close();
        return labelList;
    }

    public void updateImage(){

    }
}
