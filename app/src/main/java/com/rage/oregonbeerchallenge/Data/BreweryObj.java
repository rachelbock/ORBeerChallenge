package com.rage.oregonbeerchallenge.Data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

/**
 * Contains the data on breweries and constants for local database
 */
public class BreweryObj implements BaseColumns, Parcelable{

    //Database Column Headers
    public static final String TABLE_NAME = "visited_breweries";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_IMAGE = "image";
    public static final String COL_VISITED = "visited";

    private String id;
    private String name;
    private String image;
    private Boolean visited = false;

    public BreweryObj(String breweryId, String breweryName, String imageURL, Boolean visited) {
        id = breweryId;
        name = breweryName;
        image = imageURL;
        this.visited = visited;
    }



    public BreweryObj(String breweryId, String breweryName) {
        id = breweryId;
        name = breweryName;
      }

    protected BreweryObj(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<BreweryObj> CREATOR = new Creator<BreweryObj>() {
        @Override
        public BreweryObj createFromParcel(Parcel in) {
            return new BreweryObj(in);
        }

        @Override
        public BreweryObj[] newArray(int size) {
            return new BreweryObj[size];
        }
    };

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
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_IMAGE, image);
        contentValues.put(COL_VISITED, visited);
        return contentValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
    }
}
