package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contains beer data.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Beer {

    private String name;
    private String description;


    public Beer() {
        //No arg constructor for Jackson
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
