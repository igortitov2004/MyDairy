package com.example.myowndairy.DB;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.Fragment;

import com.example.myowndairy.FragmentCreateTaskToday;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Dairy";
    public static final String TABLE_TASKS = "tasks";
    public static final String KEY_ID = "_id";
    public static final String KEY_HEADER = "header";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_DESCRIPTION = "description";


    public DBHelper(Fragment fragment) {
        super(fragment.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_TASKS
                + " ("
                + KEY_ID + " integer primary key,"
                + KEY_HEADER + " text,"
                + KEY_DATE + " date,"
                + KEY_TIME + " time,"
                + KEY_DESCRIPTION + " text"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("drop table if exists " + TABLE_TASKS);
         onCreate(db);
    }
}
