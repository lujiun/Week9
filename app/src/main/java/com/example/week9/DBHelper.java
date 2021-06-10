package com.example.week9;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, MemoContract.DB_NAME, null, MemoContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(MemoContract.Memos.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(MemoContract.Memos.DELETE_TABLE);
        onCreate(db);
    }

    public void insertMemoBySQL(String title, int sh, int sm, int sme, int eh, int em, int eme, String place, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, '%s', %d, %d, %d, %d, %d, %d, '%s', '%s')",
                    MemoContract.Memos.TABLE_NAME,
                    MemoContract.Memos._ID,
                    MemoContract.Memos.KEY_TITLE,
                    MemoContract.Memos.KEY_S_HOUR,
                    MemoContract.Memos.KEY_S_MIN,
                    MemoContract.Memos.KEY_S_MERIDIEM,
                    MemoContract.Memos.KEY_E_HOUR,
                    MemoContract.Memos.KEY_E_MIN,
                    MemoContract.Memos.KEY_E_MERIDIEM,
                    MemoContract.Memos.KEY_PLACE,
                    MemoContract.Memos.KEY_MEMO,
                    title, sh, sm, sme, eh, em, eme, place, memo);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }
    public void deleteMemoBySQL(String _id) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    MemoContract.Memos.TABLE_NAME,
                    MemoContract.Memos._ID,
                    _id);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }
//    public void updateMemoBySQL(String _id, String name, String phone) {
//        try {
//            String sql = String.format (
//                    "UPDATE  %s SET %s = '%s', %s = '%s' WHERE %s = %s",
//                    MemoContract.Memos.TABLE_NAME,
//                    MemoContract.Memos.KEY_TITLE, name,
//                    MemoContract.Memos.KEY_S_TIME, phone,
//                    MemoContract.Memos._ID, _id) ;
//            getWritableDatabase().execSQL(sql);
//        } catch (SQLException e) {
//            Log.e(TAG,"Error in updating recodes");
//        }
//    }
    public Cursor getAllMemosBySQL() {
        String sql = "Select * FROM " + MemoContract.Memos.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }
}