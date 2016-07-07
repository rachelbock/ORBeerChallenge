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


    /**
     * Sets up an instance of the database if one has not been created already.
     * @param context - application context
     * @return database instance
     */
    public static VisitedBrewerySQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new VisitedBrewerySQLiteHelper(context.getApplicationContext(), DB_NAME, null, 1);
        }
        return instance;
    }


    /**
     * Sets up database with columns defined in the brewery object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BreweryObj.TABLE_NAME + " (" +
                BreweryObj.COL_ID + " TEXT PRIMARY KEY, " +
                BreweryObj.COL_NAME + " TEXT, " +
                BreweryObj.COL_IMAGE + " TEXT, " +
                BreweryObj.COL_VISITED + " BOOLEAN)"
        );
    }

    /**
     * No functionality for upgrading - if called will delete existing table and create
     * a new table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BreweryObj.TABLE_NAME);
        onCreate(db);
    }


    /**
     * Returns the cursor at a given column in the table.
     */
    public String getCursorString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    /**
     * Queries the database for all breweries that exist in the database. In this case, only visited
     * breweries exist in the database.
     * @return list of breweries in database.
     */
    public List<BreweryObj> getVisitedBreweries() {
        List<BreweryObj> breweries = new ArrayList<>();

        String query = "SELECT * FROM " + BreweryObj.TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        //Starts at the first row and pulls data at each column to add to the brewery object. Passing
        //default value of true for if visited as if it is in the database that would be true. Blank
        //string for the description as that is not stored in the database but is required for the
        //constructor.
        if (cursor.moveToFirst()) {
            do {
                String id = getCursorString(cursor, BreweryObj.COL_ID);
                String name = getCursorString(cursor, BreweryObj.COL_NAME);
                String image = getCursorString(cursor, BreweryObj.COL_IMAGE);
                BreweryObj breweryObj = new BreweryObj(id, name, image, true);
                breweries.add(breweryObj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return breweries;
    }

    /**
     * Adds a brewery to the table.
     */
    public void addVisitedBrewery(BreweryObj brewery) {
        getWritableDatabase().insert(BreweryObj.TABLE_NAME, null, brewery.getContentValues());
    }

    /**
     * Removes a brewery from the table.
     */
    public void removeVisitedBrewery(String breweryId) {
        String query = BreweryObj.COL_ID + " = '" + breweryId + "'";
        getWritableDatabase().delete(BreweryObj.TABLE_NAME, query, null);
    }
}
