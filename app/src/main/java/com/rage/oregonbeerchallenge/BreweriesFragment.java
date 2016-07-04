package com.rage.oregonbeerchallenge;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rage.oregonbeerchallenge.Data.BreweryData;
import com.rage.oregonbeerchallenge.Data.Location;
import com.rage.oregonbeerchallenge.Data.LocationWrapper;
import com.rage.oregonbeerchallenge.Networking.ApiManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Fragment to display list of breweries in Oregon
 */
public class BreweriesFragment extends Fragment {

    public static final String TAG = BreweriesFragment.class.getSimpleName();

    @Bind(R.id.breweries_fragment_recycler_view)
    protected RecyclerView recyclerView;
    private List<BreweryData> breweries;
    private BreweryRecyclerViewAdapter adapter;

    public BreweriesFragment() {
        // Required empty public constructor
    }


    public static BreweriesFragment newInstance(){
        return new BreweriesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_breweries, container, false);
        ButterKnife.bind(this, rootView);
        breweries = new ArrayList<BreweryData>();
        getBreweries();
        Log.d(TAG, "complete");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BreweryRecyclerViewAdapter(breweries, getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Network call to get back all of the Oregon brewery locations. Adds each brewery to the list
     * of breweries and notifies the recycler view to update with the list.
     */
    public void getBreweries() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Call<LocationWrapper> breweryCall = ApiManager.getBreweryDBService().getBreweriesByLocation();
            breweryCall.enqueue(new Callback<LocationWrapper>() {
                @Override
                public void onResponse(Call<LocationWrapper> call, Response<LocationWrapper> response) {
                    LocationWrapper locationData = response.body();
                    List<Location> locations = new ArrayList<>();
                    locations.addAll(locationData.getData());
                    HashSet<String> breweryIds = new HashSet<String>();

                    // Because the response will contain each location of a brewery, this code adds
                    // each brewery id to a set and only adds new instances of a brewery to the
                    // actual brewery list.

                    for (Location location : locations) {
                        if (!breweryIds.contains(location.getBreweryId())) {
                            breweries.add(location.getBrewery());
                            breweryIds.add(location.getBreweryId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<LocationWrapper> call, Throwable t) {
                    Log.d(TAG, "Failed to retrieve brewery data" + t);
                }
            });


        }
        else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

}
