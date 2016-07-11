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
 * Fragment to display list of breweries in Oregon.
 */
public class BreweriesFragment extends Fragment {

    public static final String TAG = BreweriesFragment.class.getSimpleName();

    @Bind(R.id.breweries_fragment_recycler_view)
    protected RecyclerView recyclerView;

    private BreweryRecyclerViewAdapter adapter;

    //List of breweries created from the API and used in the recycler view for display.
    private List<BreweryObj> breweryObjs;

    //List of breweries from the local database - breweries that have been visited.
    private List<BreweryObj> databaseBreweries;

    //List of unique brewery Ids to ensure there are no duplicate breweries.
    private HashSet<String> breweryIds;

    //Starting page number for API call.
    private int pageNumber = 1;
    private int totalPageNumbers = 1;

    public BreweriesFragment() {
        // Required empty public constructor
    }


    /**
     * Returns an instance of BreweriesFragment.
     */
    public static BreweriesFragment newInstance() {
        return new BreweriesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_breweries, container, false);
        ButterKnife.bind(this, rootView);

        breweryObjs = new ArrayList<>();
        breweryIds = new HashSet<String>();

        //Gets the visited breweries from the database and adds to the databaseBreweries list.
        VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper = VisitedBrewerySQLiteHelper.getInstance(getContext());
        databaseBreweries = visitedBrewerySQLiteHelper.getVisitedBreweries();

        //Gets all breweries from the API and updates the brewerObjs list.
        fetchBreweries();

        //Sets the brewery Recycler View with the list of breweries from the breweries db.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BreweryRecyclerViewAdapter(breweryObjs, visitedBrewerySQLiteHelper, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Network call to get back all of the Oregon brewery locations. Adds each brewery to the list
     * of breweries and notifies the recycler view to update with the list.
     */
    public void fetchBreweries() {

        //Checks for network connection - returns toast if no connection exists.
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //Makes network call to get all of the breweries in Oregon.
            final Call<LocationWrapper> breweryCall = ApiManager.getBreweryDBService().getBreweriesByLocation(pageNumber);
            breweryCall.enqueue(new Callback<LocationWrapper>() {
                @Override
                public void onResponse(Call<LocationWrapper> call, Response<LocationWrapper> response) {

                    totalPageNumbers = response.body().getNumberOfPages();

                    //Response comes in the form of LocationWrapper object. As long as the data object
                    //is not null - all of the results are added to the locations array list.
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
                                String image;

                                //Some breweries do not have images in the database. If there is no
                                //image it includes a sentinel value.
                                if (location.getBrewery().getImages() != null) {
                                    image = location.getBrewery().getImages().getSquareLarge();
                                } else {
                                    image = BreweryImages.NO_ICON;
                                }

                                //By default each brewery would not be visited. However, if the brewery
                                //is contained in the database list of breweries than it has been visited.
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

                        //Notifies the adapter that there have been updates to display.
                        adapter.notifyDataSetChanged();
                    }
                }


                @Override
                public void onFailure(Call<LocationWrapper> call, Throwable t) {
                    Log.d(TAG, "Failed to retrieve brewery data" + t);
                    Toast.makeText(getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * When the more results button is selected - as long as it is within the total number of page
     * results - calls get breweries to retrieve the next list of breweries.
     */
    @OnClick(R.id.breweries_fragment_load_more_button)
    public void onLoadMoreButtonClicked() {
        if (pageNumber <= totalPageNumbers) {
            pageNumber = pageNumber + 1;
            fetchBreweries();
        } else {
            Toast.makeText(getContext(), "There are no more results", Toast.LENGTH_SHORT).show();

        }
    }

}
