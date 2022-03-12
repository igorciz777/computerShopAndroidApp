package com.example.arrayadapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PcOrders.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContractor.Entry.TABLE_NAME + " (" +
                    DBContractor.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    DBContractor.Entry.COLUMN_USERNAME + " TEXT NOT NULL," +
                    DBContractor.Entry.COLUMN_PCSET + " TEXT NOT NULL," +
                    DBContractor.Entry.COLUMN_PCIMAGE + " TEXT," +
                    DBContractor.Entry.COLUMN_PCAMOUNT + " INTEGER NOT NULL," +
                    DBContractor.Entry.COLUMN_MOUSE + " TEXT," +
                    DBContractor.Entry.COLUMN_MOUSEIMAGE + " TEXT," +
                    DBContractor.Entry.COLUMN_KEYBOARD + " TEXT," +
                    DBContractor.Entry.COLUMN_KEYBOARDIMAGE + " TEXT," +
                    DBContractor.Entry.COLUMN_MONITOR + " TEXT," +
                    DBContractor.Entry.COLUMN_MONITORIMAGE + " TEXT," +
                    DBContractor.Entry.COLUMN_PRICE + " REAL NOT NULL," +
                    DBContractor.Entry.COLUMN_ORDERDATE + " REAL NOT NULL);";
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + DBContractor.User.TABLE_NAME + " (" +
                    DBContractor.User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    DBContractor.User.COLUMN_LOGIN + " TEXT NOT NULL," +
                    DBContractor.User.COLUMN_PASSWORD + " TEXT NOT NULL);";

    public DBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public String getSqlCreateEntries(){
        return SQL_CREATE_ENTRIES;
    }
    public void addOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBContractor.Entry.COLUMN_USERNAME, order.getUsername());
        values.put(DBContractor.Entry.COLUMN_PCSET, order.getPcSet());
        values.put(DBContractor.Entry.COLUMN_PCIMAGE, order.getPcImage());
        values.put(DBContractor.Entry.COLUMN_PCAMOUNT, order.getPcAmount());
        values.put(DBContractor.Entry.COLUMN_MOUSE, order.getMouse());
        values.put(DBContractor.Entry.COLUMN_MOUSEIMAGE, order.getMsImage());
        values.put(DBContractor.Entry.COLUMN_KEYBOARD, order.getKeyboard());
        values.put(DBContractor.Entry.COLUMN_KEYBOARDIMAGE, order.getKbImage());
        values.put(DBContractor.Entry.COLUMN_MONITOR, order.getMonitor());
        values.put(DBContractor.Entry.COLUMN_MONITORIMAGE, order.getMrImage());
        values.put(DBContractor.Entry.COLUMN_PRICE, order.getPrice());
        values.put(DBContractor.Entry.COLUMN_ORDERDATE, order.getOrderDate());

        db.insert(DBContractor.Entry.TABLE_NAME, null, values);

        db.close();
    }
    @SuppressLint("Range")
    public List<Order> getOrdersFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Order> newOrderList = new ArrayList<>();
        String[] projection = {
                DBContractor.Entry.COLUMN_PCSET,
                DBContractor.Entry.COLUMN_KEYBOARD,
                DBContractor.Entry.COLUMN_MOUSE,
                DBContractor.Entry.COLUMN_MONITOR,
                DBContractor.Entry.COLUMN_USERNAME,
                DBContractor.Entry.COLUMN_PCIMAGE,
                DBContractor.Entry.COLUMN_KEYBOARDIMAGE,
                DBContractor.Entry.COLUMN_MOUSEIMAGE,
                DBContractor.Entry.COLUMN_MONITORIMAGE,
                DBContractor.Entry.COLUMN_PCAMOUNT,
                DBContractor.Entry.COLUMN_PRICE,
                DBContractor.Entry.COLUMN_ORDERDATE
        };

        Cursor cursor = db.query(
                DBContractor.Entry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()){
            newOrderList.add(new Order(
                    cursor.getString(cursor.getColumnIndex(DBContractor.Entry.COLUMN_PCSET)),
                    cursor.getString(cursor.getColumnIndex(DBContractor.Entry.COLUMN_KEYBOARD)),
                    cursor.getString(cursor.getColumnIndex(DBContractor.Entry.COLUMN_MOUSE)),
                    cursor.getString(cursor.getColumnIndex(DBContractor.Entry.COLUMN_MONITOR)),
                    cursor.getString(cursor.getColumnIndex(DBContractor.Entry.COLUMN_USERNAME)),
                    cursor.getInt(cursor.getColumnIndex(DBContractor.Entry.COLUMN_PCIMAGE)),
                    cursor.getInt(cursor.getColumnIndex(DBContractor.Entry.COLUMN_KEYBOARDIMAGE)),
                    cursor.getInt(cursor.getColumnIndex(DBContractor.Entry.COLUMN_MOUSEIMAGE)),
                    cursor.getInt(cursor.getColumnIndex(DBContractor.Entry.COLUMN_MONITORIMAGE)),
                    cursor.getInt(cursor.getColumnIndex(DBContractor.Entry.COLUMN_PCAMOUNT)),
                    cursor.getDouble(cursor.getColumnIndex(DBContractor.Entry.COLUMN_PRICE))
            ));
        }
        cursor.close();
        db.close();
        return newOrderList;
    }
    @SuppressLint("Range")
    public String getPasswordByLogin(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DBContractor.User.COLUMN_LOGIN,
                DBContractor.User.COLUMN_PASSWORD
        };
        String selection = DBContractor.User.COLUMN_LOGIN + " = ?";
        String[] selectionArgs = {login};
        Cursor cursor = db.query(
                DBContractor.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );
        cursor.moveToLast();
        db.close();
        if (cursor.getCount()>0){
            return cursor.getString(cursor.getColumnIndex(DBContractor.User.COLUMN_PASSWORD));
        }else {
            return null;
        }
    }
    public void addNewUser(String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBContractor.User.COLUMN_LOGIN,login);
        values.put(DBContractor.User.COLUMN_PASSWORD,password);

        db.insert(DBContractor.User.TABLE_NAME,null,values);
        db.close();
    }
    public boolean checkIfLoginExists(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection={
                DBContractor.User.COLUMN_LOGIN
        };
        String selection = DBContractor.User.COLUMN_LOGIN + " = ?";
        String[] selectionArgs = {login};
        Cursor cursor = db.query(
                DBContractor.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );
        return cursor.getCount() > 0;
    }
    public void deleteDB(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
