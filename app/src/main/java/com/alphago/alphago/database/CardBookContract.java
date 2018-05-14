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
        public static final String COLUMN_NAME_JAP_LABEL = "category_label_jap";
        public static final String COLUMN_NAME_CHI_LABEL = "category_label_chi";
        public static final String COLUMN_NAME_PATH = "img_path";
    }

    public static class CardBookEntry {
        public static final String TABLE_NAME = "card_book";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_CATEGORY = "category_id"; // 카테고리 기본키
        public static final String COLUMN_NAME_LABEL = "card_book_label";
        public static final String COLUMN_NAME_JAP_LABEL = "card_book_jap_label";
        public static final String COLUMN_NAME_CHI_LABEL = "card_book_chi_label";
        public static final String COLUMN_NAME_PATH = "img_path";
        public static final String COLUMN_NAME_COLLECT = "collect";
    }

    public static class CardsEntry implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_NAME_IMG = "label_id"; // 카드북 기본키
        public static final String COLUMN_NAME_LABEL = "card_book_label"; // 나중에 문제 내기 쉽게
        public static final String COLUMN_NAME_PATH = "img_path";
    }

    public static class CollectionEntry implements BaseColumns{
        public static final String TABLE_NAME = "collection";
        public static final String COLUMN_NAME_LABEL = "label";
        public static final String COLUMN_NAME_JAP_LABEL = "jap_label";
        public static final String COLUMN_NAME_CHI_LABEL = "chi_label";
        public static final String COLUMN_NAME_CAT_ID = "category_id";
        public static final String COLUMN_NAME_LABEL_ID = "label_id";
        public static final String COLUMN_NAME_PATH = "img_path";
        public static final String COLUMN_NAME_COLLECT = "collect";
    }
}
