package com.rage.oregonbeerchallenge.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Sets up Retrofit
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
