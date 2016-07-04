package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Contains list of locations in data object
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationWrapper {

    private List<Location> data;

    public LocationWrapper() {
        //No arg constructor for Jackson
    }

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }
}
