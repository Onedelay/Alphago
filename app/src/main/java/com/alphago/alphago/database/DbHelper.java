package com.alphago.alphago.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alphago.alphago.database.CardBookContract.*;
import com.alphago.alphago.model.Card;
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

    private static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryEntry.COLUMN_NAME_LABEL + " TEXT ," +
                    CategoryEntry.COLUMN_NAME_PATH + " TEXT)";

    private static final String SQL_CREATE_CARD_BOOK =
            "CREATE TABLE " + CardBookEntry.TABLE_NAME + " (" +
                    CardBookEntry._ID + " INTEGER PRIMARY KEY," +
                    CardBookEntry.COLUMN_NAME_CATEGORY + " INTERGER ," +
                    CardBookEntry.COLUMN_NAME_LABEL + " TEXT ," +
                    CardBookEntry.COLUMN_NAME_PATH + " TEXT ,"+
                    CardBookEntry.COLUMN_NAME_COLLECT+" INTEGER)";

    private static final String SQL_CREATE_CARDS =
            "CREATE TABLE " + CardsEntry.TABLE_NAME + " (" +
                    CardsEntry._ID + " INTEGER PRIMARY KEY," +
                    CardsEntry.COLUMN_NAME_IMG + " INTEGER ," +
                    CardsEntry.COLUMN_NAME_LABEL + " TEXT ," +
                    CardsEntry.COLUMN_NAME_PATH + " TEXT)";

    // Default images
    private static final String[] CATEGORY_LIST = {"animal", "furniture", "food", "school", "kitchen", "electronics", "bathroom", "room", "clothes", "ETC" };
    private static final String[][] IMAGE_LIST = {
            {"animal","cat","1","1"}, {"animal","dog","1","2"}, {"animal","lion","1","3"},
            {"food","apple","3","9"}, {"food","potato","3","13"},
            {"furniture","bed","2","20"}, {"furniture","chair","2","21"}, {"furniture","desk","2","22"}
    };

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CARD_BOOK);
        db.execSQL(SQL_CREATE_CATEGORY);

        /*
        * default image 초기화 부분, 서버에서 주면 좋다고함.
        * */

        // 카테고리 초기화
        for (String category : CATEGORY_LIST) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME_LABEL, category);
            db.insert(CategoryEntry.TABLE_NAME, null, values); // return row ID (long)
        }

        // 카드북 초기화
        for(int i=0; i<IMAGE_LIST.length; i++){
            ContentValues values = new ContentValues();
            values.put(CardBookEntry._ID, IMAGE_LIST[i][3]);
            values.put(CardBookEntry.COLUMN_NAME_LABEL, IMAGE_LIST[i][1]);
            values.put(CardBookEntry.COLUMN_NAME_CATEGORY, IMAGE_LIST[i][2]);
            db.insert(CardBookEntry.TABLE_NAME, null, values);
        }

        // 카드 초기화
        for(int i=0; i<IMAGE_LIST.length; i++){
            ContentValues values = new ContentValues();
            values.put(CardsEntry.COLUMN_NAME_IMG, IMAGE_LIST[i][3]);
            values.put(CardsEntry.COLUMN_NAME_LABEL, IMAGE_LIST[i][1]);
            db.insert(CardsEntry.TABLE_NAME, null, values);
        }

        // 같은 레이블 가진 다른 사진들
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Category> categorySelect() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        Cursor c = db.query(CategoryEntry.TABLE_NAME, projection, null, null, null, null, null);

        String selection = CardBookEntry.COLUMN_NAME_CATEGORY + " = ?";
        List<Category> categoryList = new ArrayList<>();

        Cursor cards = null;

        while (c.moveToNext()) {
            long categoryId = c.getLong(c.getColumnIndexOrThrow(CategoryEntry._ID));
            String category = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_LABEL));

            String[] selectionArgs = {String.valueOf(categoryId)};
            cards = db.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            if(cards.moveToNext()) categoryList.add(new Category(categoryId, category, null));
        }

        cards.close();
        c.close();
        return categoryList;
    }

    public List<CardBook> cardbookSelect(long categoryId) {
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

    public List<Card> cardsSelect(long imgId){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        String selection = CardsEntry.COLUMN_NAME_IMG + " = ?";
        String[] selectionArgs = {String.valueOf(imgId)};

        Cursor c = db.query(CardsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        List<Card> cardList = new ArrayList<>();

        while (c.moveToNext()) {
            long cardId = c.getLong(c.getColumnIndexOrThrow(CardsEntry._ID));
            String path = c.getString(c.getColumnIndexOrThrow(CardsEntry.COLUMN_NAME_PATH));
            cardList.add(new Card(cardId, imgId, path));
        }
        c.close();
        return cardList;
    }

    public void insertImage(String categoryName, String imageLabel, int catId, int labelId, String filePath){

        SQLiteDatabase rdb = getReadableDatabase();
        SQLiteDatabase wdb = getWritableDatabase();

        String[] projection = {"*"};
        String selection = CardBookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(labelId)};

        Cursor c = rdb.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        // 카드북에 현재 레이블이 없으면 삽입
        if( c == null ){
            ContentValues cardBookValues = new ContentValues();
            cardBookValues.put(CardBookEntry._ID, labelId);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_CATEGORY, catId);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_LABEL, imageLabel);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_PATH, filePath);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_COLLECT, true);
            wdb.insert(CardBookEntry.TABLE_NAME, null, cardBookValues);
        }

        ContentValues values = new ContentValues();
        values.put(CardsEntry.COLUMN_NAME_IMG, labelId);
        values.put(CardsEntry.COLUMN_NAME_LABEL, imageLabel);
        values.put(CardsEntry.COLUMN_NAME_PATH, filePath);
        wdb.insert(CardsEntry.TABLE_NAME, null, values);
    }
}
