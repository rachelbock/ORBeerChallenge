package com.rage.oregonbeerchallenge.Data;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Contains the data on breweries and constants for local database
 */
public class BreweryObj implements BaseColumns{

    //Database Column Headers
    public static final String TABLE_NAME = "visited_breweries";
    public static final String COL_ID = "id";
    public static final String COL_Name = "name";
    public static final String COL_IMAGE = "image";

    private String id;
    private String name;
    private String image;
    private Boolean visited;

    public BreweryObj(String breweryId, String breweryName, String imageURL, Boolean visited) {
        id = breweryId;
        name = breweryName;
        image = imageURL;
        this.visited = visited;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_Name, name);
        contentValues.put(COL_IMAGE, image);
        return contentValues;
    }

}
