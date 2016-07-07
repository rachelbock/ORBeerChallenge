package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Data object pulled from API to contain beer data.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeerWrapper {

    private List<Beer> data;

    public BeerWrapper() {
        //no arg constructor for Jackson
    }

    public List<Beer> getData() {
        return data;
    }

    public void setData(List<Beer> data) {
        this.data = data;
    }
}
