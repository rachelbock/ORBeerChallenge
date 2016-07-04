package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contains data gathered from API for breweries.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreweryData {

    private String id;
    private String name;
    private BreweryImages images;

    public BreweryData(){
        //No Arg constructor for Jackson
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

    public BreweryImages getImages() {
        return images;
    }

    public void setImages(BreweryImages images) {
        this.images = images;
    }

}
