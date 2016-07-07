package com.rage.oregonbeerchallenge.Networking;

import com.rage.oregonbeerchallenge.Data.BeerWrapper;
import com.rage.oregonbeerchallenge.Data.LocationWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for network calls to BreweryData DB
 */
public interface BreweryDBService {

    //Request to get all of the results where the location is Oregon.
    @GET("locations?key=" + ApiManager.API_KEY_VALUE + "&region=Oregon")
    Call<LocationWrapper> getBreweriesByLocation(@Query("p") int currentPage);

    //Request to get all of the beers by Brewery ID
    @GET("brewery/{brewery_id}/beers?key=" + ApiManager.API_KEY_VALUE)
    Call<BeerWrapper> getBeersByBrewery(@Path("brewery_id") String brewery_id);

}
