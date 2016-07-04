package com.rage.oregonbeerchallenge.Networking;

import com.rage.oregonbeerchallenge.Data.LocationWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for network calls to BreweryData DB
 */
public interface BreweryDBService {

    //Request to get all of the results where the location is Oregon.
    @GET("locations?key=" + ApiManager.API_KEY_VALUE + "&region=Oregon")
    Call<LocationWrapper> getBreweriesByLocation();

}
