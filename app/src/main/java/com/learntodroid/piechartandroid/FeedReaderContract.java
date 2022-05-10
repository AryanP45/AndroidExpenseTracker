package com.learntodroid.piechartandroid;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_Amount = "amount";
        public static final String COLUMN_NAME_Category = "category";
        public static final String COLUMN_NAME_Date = "date";
        public static final String COLUMN_NAME_Note = "note";

        public static String getTableName() {
            return TABLE_NAME;
        }

        public static String getCOLUMN_NAME_Amount() {
            return COLUMN_NAME_Amount;
        }

        public static String getCOLUMN_NAME_Category() {
            return COLUMN_NAME_Category;
        }

        public static String getCOLUMN_NAME_Date() {
            return COLUMN_NAME_Date;
        }

        public static String getCOLUMN_NAME_Note() {
            return COLUMN_NAME_Note;
        }
    }

}