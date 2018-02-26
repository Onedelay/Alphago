package com.alphago.alphago.database;

import android.provider.BaseColumns;

/**
 * Created by su_me on 2018-02-25.
 */

public final class CardBookContract {

    private CardBookContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_LABEL = "category_label";
        public static final String COLUMN_NAME_PATH = "img_path";
    }

    public static class CardBookEntry implements BaseColumns {
        public static final String TABLE_NAME = "card_book";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_PATH = "img_path";
        public static final String COLUMN_NAME_COLLECT = "collect";
    }

    public static class CardsEntry implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_NAME_IMG = "img_id";
        public static final String COLUMN_NAME_PATH = "img_path";
    }
}
