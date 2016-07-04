package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Holds location data
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private String id;
    private String locality;
    private String breweryId;
    private BreweryData brewery;

    public Location() {
        //No arg constructor for Jackson
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(String breweryId) {
        this.breweryId = breweryId;
    }

    public BreweryData getBrewery() {
        return brewery;
    }

    public void setBrewery(BreweryData brewery) {
        this.brewery = brewery;
    }
}
