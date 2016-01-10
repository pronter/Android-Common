package com.example.su.zuhe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by su on 2016/1/3.
 */
public class heiheiDatebaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "Jishiben.db";
        private static final String TABLE_NAME = "Jishiben";
        private static final int DB_VERSION = 1;

        public heiheiDatebaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, " + "_name text not null, _time text);";
            db.execSQL(CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
            onCreate(db);
        }

        public long insert(Contact entity) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_name", entity.getName());
            values.put("_time", entity.getTime());

            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return id;
        }

        public int update(Contact entity, String where) {
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = "_name = ?";
            String[] whereArgs = {where};

            ContentValues values = new ContentValues();
            values.put("_name", entity.getName());
            values.put("_time", entity.getTime());

            int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
            db.close();
            return rows;
        }

        public int delete(Contact entity) {
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = "_name = ?";
            String[] whereArgs = {entity.getName()};

            int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
            db.close();
            return rows;
        }

        public Cursor query() {
            SQLiteDatabase db = getWritableDatabase();
            return db.query(TABLE_NAME, null, null, null, null, null, null);
        }

}

