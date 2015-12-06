package com.example.zijia.hw7;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zijia on 2015/12/6.
 */
//创建MyDatabaseHelper类
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //创建数据库，执行创建数据库的SQL语句
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, " +
                "_no text not null, _name text not null, _pnumber text);";
        db.execSQL(CREATE_TABLE);
    }

    //重载onUpgrade实例化
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    //数据库插入功能
    public long insert(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_no", entity.getNo());
        values.put("_name", entity.getName());
        values.put("_pnumber", entity.getPnumber());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    //数据库更新功能
    public int update(Contact entity, String where) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_no = ?";
        String[] whereArgs = {where};
        ContentValues values = new ContentValues();
        values.put("_no", entity.getNo());
        values.put("_name", entity.getName());
        values.put("_pnumber", entity.getPnumber());
        int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    //数据库删除功能
    public int delete(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_no = ?";
        String[] whereArgs = {entity.getNo()};

        int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rows;
    }

    //数据库查询操作
    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

}
