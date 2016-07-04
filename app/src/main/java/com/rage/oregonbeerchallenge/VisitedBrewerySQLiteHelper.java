package com.rage.oregonbeerchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rage.oregonbeerchallenge.Data.BreweryObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Local Database setup.
 */
public class VisitedBrewerySQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "visited_breweries.db";
    private static VisitedBrewerySQLiteHelper instance;

    public VisitedBrewerySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static VisitedBrewerySQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new VisitedBrewerySQLiteHelper(context.getApplicationContext(), DB_NAME, null, 1);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreweryObj.TABLE_NAME + "(" +
                BreweryObj.COL_ID + " TEXT, " +
                BreweryObj.COL_Name + " TEXT, " +
                BreweryObj.COL_IMAGE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public String getCursorString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public List<BreweryObj> getVisitedBreweries() {
        List<BreweryObj> breweries = new ArrayList<>();

        String query = "SELECT * FROM " + BreweryObj.TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String id = getCursorString(cursor, BreweryObj.COL_ID);
                String name = getCursorString(cursor, BreweryObj.COL_Name);
                String image = getCursorString(cursor, BreweryObj.COL_IMAGE);
                BreweryObj breweryObj = new BreweryObj(id, name, image, true);
                breweries.add(breweryObj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return breweries;
    }

    public void addVisitedBrewery(BreweryObj brewery) {
        getWritableDatabase().insert(BreweryObj.TABLE_NAME, null, brewery.getContentValues());
    }

    public void removeVisitedBrewery(String breweryId) {
        String query = BreweryObj.COL_ID + " = " + breweryId;
        getWritableDatabase().delete(BreweryObj.TABLE_NAME, query, null);
    }
}
