package com.alphago.alphago.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.alphago.alphago.database.CardBookContract.*;
import com.alphago.alphago.model.Card;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;
import com.alphago.alphago.model.CollectCategory;
import com.alphago.alphago.model.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by su_me on 2018-02-25.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CARD_BOOK.db";
    private Context context;

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

    private static final String SQL_CREATE_COLLECTION =
            "CREATE TABLE " + CollectionEntry.TABLE_NAME + " (" +
                    CollectionEntry._ID + " INTEGER PRIMARY KEY," +
                    CollectionEntry.COLUMN_NAME_LABEL + " TEXT ,"+
                    CollectionEntry.COLUMN_NAME_CAT_ID + " INTEGER ," +
                    CollectionEntry.COLUMN_NAME_LABEL_ID + " INTEGER ,"+
                    CollectionEntry.COLUMN_NAME_PATH + " TEXT ,"+
                    CollectionEntry.COLUMN_NAME_COLLECT+" INTEGER)";

    private static final String[] CATEGORY_LIST = {"Animal", "Outdoor", "Food", "School", "Kitchen", "Bathroom", "Electronics", "Room"};

    private static final String[][] ANIMAL_COLLECTION = {
            {"Cat","1"},{"Dog","2"},{"Lion","3"},{"Bear","33"},{"Hippo","41"},  {"Deer","35"},{"Elephant","36"},{"Rabbit","42"},{"Frog","43"},{"Horse","44"},
            {"Chicken","37"},{"Alligator","38"},{"Duck","40"}
    };

    private static final String[][] OUTDOOR_COLLECTION = {
            {"Car","52"},{"Bicycle","53"},{"Motorcycle","54"},{"Bus","55"},{"Bench","56"}
    };

    private static final String[][] FOOD_COLLECTION = {
            {"Apple","9"},{"Banana","10"},{"Cucumber","11"},{"Lemon","12"},{"Potato","13"},  {"Strawberry","15"},{"Pumpkin","16"},
            {"Tomato","14"}
    };

    private static final String[][] SCHOOL_COLLECTION = {
            {"Chair","21"},{"Desk","22"},{"Pen","23"},{"Pencil","24"},{"Eraser","28"},{"Mop","57"},{"Broom","58"}
    };

    private static final String[][] KITCHEN_COLLECTION = {
            {"Cup","17"},{"Knife","18"},{"Scissor","19"},{"Tumbler","25"},{"Bottle","29"}
    };

    private static final String[][] BATHROOM_COLLECTION = {
            {"Toilet","45"},{"Tub","46"},{"Toothbrush","48"},{"Basin","49"},{"Toilet paper","47"}
    };

    private static final String[][] ELECTRONICS_COLLECTION = {
            {"Laptop","5"},{"Mike","6"},{"Monitor","7"},{"Usb","8"},{"Cellphone","26"},{"Television","27"},{"Mouse","31"},{"Keyboard","32"}
    };

    private static final String[][] ROOM_COLLECTION = {
            {"Bed","20"},{"Umbrella","30"},{"Sofa","50"},{"Clock","51"}
    };

    private static final ArrayList<String[][]> COLLECTION_LIST = new ArrayList<>();
    static {
        COLLECTION_LIST.add(ANIMAL_COLLECTION);
        COLLECTION_LIST.add(OUTDOOR_COLLECTION);
        COLLECTION_LIST.add(FOOD_COLLECTION);
        COLLECTION_LIST.add(SCHOOL_COLLECTION);
        COLLECTION_LIST.add(KITCHEN_COLLECTION);
        COLLECTION_LIST.add(BATHROOM_COLLECTION);
        COLLECTION_LIST.add(ELECTRONICS_COLLECTION);
        COLLECTION_LIST.add(ROOM_COLLECTION);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CARD_BOOK);
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_CARDS);
        db.execSQL(SQL_CREATE_COLLECTION);

        // 카테고리 초기화
        for (String category : CATEGORY_LIST) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME_LABEL, category);
            db.insert(CategoryEntry.TABLE_NAME, null, values); // return row ID (long)
        }

        // 컬렉션 초기화
        for(int i=0; i<COLLECTION_LIST.size(); i++){
            for(int j=0; j<COLLECTION_LIST.get(i).length; j++){
                ContentValues values = new ContentValues();
                values.put(CollectionEntry.COLUMN_NAME_CAT_ID, i+1);
                values.put(CollectionEntry.COLUMN_NAME_LABEL, COLLECTION_LIST.get(i)[j][0]);
                values.put(CollectionEntry.COLUMN_NAME_LABEL_ID, COLLECTION_LIST.get(i)[j][1]);
                values.put(CollectionEntry.COLUMN_NAME_COLLECT, 0);
                db.insert(CollectionEntry.TABLE_NAME, null, values);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<CollectCategory> categoryAllSelect() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        Cursor c = db.query(CategoryEntry.TABLE_NAME, projection, null, null, null, null, null);

        String selection = CardBookEntry.COLUMN_NAME_CATEGORY + " = ?";
        List<CollectCategory> categoryList = new ArrayList<>();


        while (c.moveToNext()) {
            long categoryId = c.getLong(c.getColumnIndexOrThrow(CategoryEntry._ID));
            String category = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_LABEL));
            String filePath = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_PATH));

            String[] selectionArgs = {String.valueOf(categoryId)};
            Cursor cards = db.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            categoryList.add(new CollectCategory(categoryId, category, filePath, getAchievementRate(categoryId)));
            cards.close();
        }

        c.close();

        return categoryList;
    }

    public List<Category> categorySelect() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        Cursor c = db.query(CategoryEntry.TABLE_NAME, projection, null, null, null, null, null);

        String selection = CardBookEntry.COLUMN_NAME_CATEGORY + " = ?";
        List<Category> categoryList = new ArrayList<>();

        while (c.moveToNext()) {
            long categoryId = c.getLong(c.getColumnIndexOrThrow(CategoryEntry._ID));
            String category = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_LABEL));
            String filePath = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_PATH));

            String[] selectionArgs = {String.valueOf(categoryId)};
            Cursor cards = db.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            if(cards.moveToNext()) categoryList.add(new Category(categoryId, category, filePath));
            cards.close();
        }

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
        Collections.reverse(labelList);
        return labelList;
    }

    public List<Card> cardsSelect(long labelId){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        String selection = CardsEntry.COLUMN_NAME_IMG + " = ?";
        String[] selectionArgs = {String.valueOf(labelId)};

        Cursor c = db.query(CardsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        List<Card> cardList = new ArrayList<>();

        while (c.moveToNext()) {
            long cardId = c.getLong(c.getColumnIndexOrThrow(CardsEntry._ID));
            String path = c.getString(c.getColumnIndexOrThrow(CardsEntry.COLUMN_NAME_PATH));
            String label = c.getString(c.getColumnIndexOrThrow(CardsEntry.COLUMN_NAME_LABEL));
            cardList.add(new Card(cardId, labelId, path, label));
        }
        c.close();
        Collections.reverse(cardList);
        return cardList;
    }

    public List<Collection> collectionSelect(long catId){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"*"};
        String selection = CollectionEntry.COLUMN_NAME_CAT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(catId)};

        Cursor c = db.query(CollectionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        List<Collection> collections = new ArrayList<>();

        while(c.moveToNext()){
            String label = c.getString(c.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_LABEL));
            long labelId = c.getLong(c.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_LABEL_ID));
            String filePath = c.getString(c.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_PATH));
            int collected = c.getInt(c.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_COLLECT));
            collections.add(new Collection(label, labelId, filePath, collected));
        }
        c.close();

        return collections;
    }

    // 컬렉션 달성률
    public float getAchievementRate(long catId){
        List<Collection> collections = collectionSelect(catId);
        int count=0;
        for(int i=0; i<collections.size(); i++)
            if(collections.get(i).getCollected() == 1) count++;
        if(collections.size() > 0) return (float)count/collections.size()*100;
        else return 0;
    }

    public void insertImage(String imageLabel, int catId, int labelId, String filePath, boolean addCollection){
        SQLiteDatabase rdb = getReadableDatabase();
        SQLiteDatabase wdb = getWritableDatabase();

        // 카테고리 - 최근 이미지로 갱신
        ContentValues catValue = new ContentValues();
        catValue.put(CategoryEntry.COLUMN_NAME_PATH, filePath);
        String catSelection = CategoryEntry._ID + " = ?";
        String[] catSelArgs = {String.valueOf(catId)};
        rdb.update(CategoryEntry.TABLE_NAME, catValue, catSelection, catSelArgs);

        String[] projection = {"*"};

        if(addCollection) {
            // 컬렉션 - collect 체크
            // 이미 1이 되어있을 때를 대비해 분기를 추가해야할까? - 했는데 맞는건지 모름
            String colSelection = CollectionEntry.COLUMN_NAME_LABEL_ID + " = ?";
            String[] colSelArgs = {String.valueOf(labelId)};

            Cursor collection = rdb.query(CollectionEntry.TABLE_NAME, projection, colSelection, colSelArgs, null, null, null);

            ContentValues colValue = new ContentValues();
            if (collection.moveToNext()) { // 컬렉션 테이블에 존재하지 않을 경우 갱신 안함
                if (collection.getInt(collection.getColumnIndexOrThrow(CollectionEntry.COLUMN_NAME_COLLECT)) == 0) {
                    colValue.put(CollectionEntry.COLUMN_NAME_COLLECT, 1);
                    colValue.put(CollectionEntry.COLUMN_NAME_PATH, filePath);
                    rdb.update(CollectionEntry.TABLE_NAME, colValue, colSelection, colSelArgs);
                    Toast.makeText(context, "컬렉션에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    colValue.put(CollectionEntry.COLUMN_NAME_PATH, filePath);
                    rdb.update(CollectionEntry.TABLE_NAME, colValue, colSelection, colSelArgs);
                    Toast.makeText(context, "컬렉션 이미지가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            collection.close();
        }

        String selection = CardBookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(labelId)};

        Cursor c = rdb.query(CardBookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        // 카드북에 현재 레이블이 없으면 삽입
        if(!c.moveToNext()){
            //Log.v("alphago", categoryName+" : "+imageLabel);
            ContentValues cardBookValues = new ContentValues();
            cardBookValues.put(CardBookEntry._ID, labelId);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_CATEGORY, catId);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_LABEL, imageLabel);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_PATH, filePath);
            cardBookValues.put(CardBookEntry.COLUMN_NAME_COLLECT, true);
            wdb.insert(CardBookEntry.TABLE_NAME, null, cardBookValues);
        } else {
            ContentValues bookValue = new ContentValues();
            bookValue.put(CardBookEntry.COLUMN_NAME_PATH, filePath);
            String bookSelection = CardBookEntry._ID + " = ?";
            String[] bookSelArgs = {String.valueOf(labelId)};
            rdb.update(CardBookEntry.TABLE_NAME, bookValue, bookSelection, bookSelArgs);
        }
        c.close();

        ContentValues values = new ContentValues();
        values.put(CardsEntry.COLUMN_NAME_IMG, labelId);
        values.put(CardsEntry.COLUMN_NAME_LABEL, imageLabel);
        values.put(CardsEntry.COLUMN_NAME_PATH, filePath);
        wdb.insert(CardsEntry.TABLE_NAME, null, values);
    }
}
