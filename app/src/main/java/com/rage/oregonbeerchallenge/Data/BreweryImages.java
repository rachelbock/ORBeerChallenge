package com.rage.oregonbeerchallenge.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data for brewery images
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreweryImages {

    private String icon;
    public static final String NO_ICON = "https://upload.wikimedia.org/wikipedia/commons/a/ac/No_image_available.svg";

    public BreweryImages(){
        //No arg constructor for images
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
