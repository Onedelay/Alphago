package com.alphago.alphago.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
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
                    CardsEntry.COLUMN_NAME_PATH + " TEXT)";

    // Default images
    private static final String[] CATEGORY_LIST = {"animal", "fruit", "furniture", "vegetable"};
    private static final String IMAGE_LIST_ANIMAL[] = {"dog", "cat", "lion"};
    private static final String IMAGE_LIST_FRUIT[] = {"apple", "banana", "strawberry", "lemon"};
    private static final String IMAGE_LIST_FURNITURE[] = {"bed", "chair", "desk"};
    private static final String IMAGE_LIST_VEGETABLE[] = {"tomato", "cucumber", "potato", "pumpkin"};

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

        // 카테고리
        for (String category : CATEGORY_LIST) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME_LABEL, category);
            db.insert(CategoryEntry.TABLE_NAME, null, values); // return row ID (long)
        }

        // 카테고리 별 사진들
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

        // 같은 레이블 가진 다른 사진들
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
//
//    public void insertImage(String categoryName, String imageLabel, String filePath){
//        SQLiteDatabase rdb = getReadableDatabase();
//        SQLiteDatabase wdb = getWritableDatabase();
//
//        String[] projection = {"_ID"};
//        String[] selectionArgs = {categoryName};
//
//        Cursor c = rdb.query(CategoryEntry.TABLE_NAME, projection, CategoryEntry.COLUMN_NAME_LABEL + " = ?", selectionArgs, null, null, null);
//        c.moveToNext();
//        long imgId = c.getColumnIndexOrThrow(CategoryEntry._ID);
//
//        /*
//        * 1. 'CATEGORY'에서 '카테고리명(categoryName)'으로 '카테고리번호'를 조회한다
//        * 2. 'CARD_BOOK'에 삽입할 '이미지명'이 있다면 해당 이미지의 '이미지 번호'를 조회하고
//        *    없다면 새로운 '이미지 번호'와 1번에서 조회한 '카테고리명', '이미지명(imageLabel)', '대표이미지'로 새로운 행을 삽입한다.
//        * 3. 'CARDS'에 2번의 '이미지 번호', '이미지 경로(filePath)'를 삽입한다
//        * */
//
//        // 카드북 테이블에 해당 레이블이 없으면 삽입 후 대표 이미지 갱신
//        selectionArgs[0] = imageLabel;
//
//        c = rdb.query(CardBookEntry.TABLE_NAME, projection, CategoryEntry.COLUMN_NAME_LABEL + " = ?", selectionArgs, null, null, null);
//        c.moveToNext();
//        long imgId = c.getColumnIndexOrThrow(CardBookEntry._ID);
//
//        ContentValues values = new ContentValues();
//        values.put(CardsEntry.COLUMN_NAME_IMG, imgId);
//        values.put(CardBookEntry.COLUMN_NAME_CATEGORY, 3);
//        wdb.insert(CardBookEntry.TABLE_NAME, null, values);
//    }
}
