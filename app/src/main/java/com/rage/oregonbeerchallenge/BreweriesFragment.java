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
import android.widget.Button;
import android.widget.Toast;

import com.rage.oregonbeerchallenge.Adapters.BreweryRecyclerViewAdapter;
import com.rage.oregonbeerchallenge.Data.BreweryImages;
import com.rage.oregonbeerchallenge.Data.BreweryObj;
import com.rage.oregonbeerchallenge.Data.Location;
import com.rage.oregonbeerchallenge.Data.LocationWrapper;
import com.rage.oregonbeerchallenge.Networking.ApiManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Fragment to display list of breweryDatas in Oregon
 */
public class BreweriesFragment extends Fragment {

    public static final String TAG = BreweriesFragment.class.getSimpleName();
    public static final int TOTAL_PAGES = 6;

    @Bind(R.id.breweries_fragment_recycler_view)
    protected RecyclerView recyclerView;
    @Bind(R.id.breweries_fragment_load_more_button)
    protected Button loadMoreButton;
    private BreweryRecyclerViewAdapter adapter;
    private List<BreweryObj> breweryObjs;
    private List<BreweryObj> databaseBreweries;
    private HashSet<String> breweryIds;
    private int pageNumber = 1;

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
        breweryObjs = new ArrayList<>();

        breweryIds = new HashSet<String>();
        VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper = VisitedBrewerySQLiteHelper.getInstance(getContext());

        databaseBreweries = new ArrayList<>();
        databaseBreweries = visitedBrewerySQLiteHelper.getVisitedBreweries();

        getBreweries();

        //Sets the brewery Recycler View with the list of breweries from the breweries db.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BreweryRecyclerViewAdapter(breweryObjs, visitedBrewerySQLiteHelper, getActivity(), getContext());
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
            final Call<LocationWrapper> breweryCall = ApiManager.getBreweryDBService().getBreweriesByLocation(pageNumber);
            breweryCall.enqueue(new Callback<LocationWrapper>() {
                @Override
                public void onResponse(Call<LocationWrapper> call, Response<LocationWrapper> response) {
                    LocationWrapper locationData = response.body();
                    List<Location> locations = new ArrayList<>();
                    if (locationData.getData() != null) {
                        locations.addAll(locationData.getData());


                        // Because the response will contain each location of a brewery, this code adds
                        // each brewery id to a set and only adds new instances of a brewery to the
                        // actual brewery list.

                        for (Location location : locations) {
                            if (!breweryIds.contains(location.getBreweryId())) {
                                String id = location.getBrewery().getId();
                                String name = location.getBrewery().getName();
                                String image = "";
                                if (location.getBrewery().getImages() != null) {
                                    image += location.getBrewery().getImages().getSquareLarge();
                                } else {
                                    image += BreweryImages.NO_ICON;
                                }
                                Boolean visited = false;
                                for (BreweryObj databaseBrewery : databaseBreweries) {
                                    if (location.getBreweryId().equals(databaseBrewery.getId())) {
                                        visited = true;
                                    }
                                }
                                BreweryObj breweryObj = new BreweryObj(id, name, image, visited);
                                breweryObjs.add(breweryObj);
                                breweryIds.add(location.getBreweryId());
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
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

    /**
     * When the more results button is selected - as long as it is within the total number of page
     * results - calls get breweries to retrieve the next list of breweries.
     */
    @OnClick(R.id.breweries_fragment_load_more_button)
    public void onLoadMoreButtonClicked() {
        if (pageNumber <= TOTAL_PAGES) {
            pageNumber = pageNumber + 1;
            getBreweries();
        }
        else {
            Toast.makeText(getContext(), "There are no more results", Toast.LENGTH_SHORT).show();

        }
    }

}
