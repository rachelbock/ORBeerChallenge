package com.rage.oregonbeerchallenge.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Sets up Retrofit. Central location for access to retrofit client. Sets it up one time at startup and
 * all subsequent calls can use it. contains constants for API don't have to be keep.
 */
public class ApiManager {

    public static final String API_KEY_VALUE = "c05055f95aed52349e5f63991e4ada0e";

    private static final String API_URL = "http://api.brewerydb.com/v2/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private static final BreweryDBService breweryDBService = retrofit.create(BreweryDBService.class);

    public static BreweryDBService getBreweryDBService() {
        return breweryDBService;
    }

}
