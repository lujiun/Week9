package com.example.week9;

import android.provider.BaseColumns;

public final class MemoContract {
    public static final String DB_NAME="memo.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MemoContract() {}

    /* Inner class that defines the table contents */
    public static class Memos implements BaseColumns {
        public static final String TABLE_NAME= "Memo";
        public static final String KEY_TITLE = "title";
        public static final String KEY_S_HOUR = "sHour";
        public static final String KEY_S_MIN = "sMin";
        public static final String KEY_S_MERIDIEM = "sMeridiem";
        public static final String KEY_E_HOUR = "eHour";
        public static final String KEY_E_MIN = "eMin";
        public static final String KEY_E_MERIDIEM = "eMeridiem";
        public static final String KEY_PLACE = "place";
        public static final String KEY_MEMO = "memo";
        public static final String KEY_YEAR = "year";
        public static final String KEY_MONTH = "monty";
        public static final String KEY_DAY = "day";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_S_HOUR + INT_TYPE + COMMA_SEP +
                KEY_S_MIN + INT_TYPE + COMMA_SEP +
                KEY_S_MERIDIEM + INT_TYPE + COMMA_SEP +
                KEY_E_HOUR + INT_TYPE + COMMA_SEP +
                KEY_E_MIN + INT_TYPE + COMMA_SEP +
                KEY_E_MERIDIEM + INT_TYPE + COMMA_SEP +
                KEY_PLACE + TEXT_TYPE + COMMA_SEP +
                KEY_MEMO + TEXT_TYPE + COMMA_SEP +
                KEY_YEAR + INT_TYPE + COMMA_SEP +
                KEY_MONTH + INT_TYPE + COMMA_SEP +
                KEY_DAY + INT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
