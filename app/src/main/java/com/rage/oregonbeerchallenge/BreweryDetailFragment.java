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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rage.oregonbeerchallenge.Adapters.BeerRecyclerViewAdapter;
import com.rage.oregonbeerchallenge.Data.Beer;
import com.rage.oregonbeerchallenge.Data.BeerWrapper;
import com.rage.oregonbeerchallenge.Data.BreweryObj;
import com.rage.oregonbeerchallenge.Networking.ApiManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Launched when brewery row is selected. Contains beer list and brewery details.
 */
public class BreweryDetailFragment extends Fragment {

    public static final String TAG = BreweryDetailFragment.class.getSimpleName();
    public static final String ARG_BREWERY = "brewery_argument";
    private BreweryObj brewery;
    private List<Beer> beerList;

    @Bind(R.id.detail_page_brewery_image)
    protected ImageView breweryImage;
    @Bind(R.id.detail_page_brewery_name_text_view)
    protected TextView breweryName;
    @Bind(R.id.detail_page_recycler_view)
    protected RecyclerView recyclerView;
    private BeerRecyclerViewAdapter adapter;


    public BreweryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Returns an instance of the fragment taking in a brewery object.
     */
    public static BreweryDetailFragment newInstance(BreweryObj brewery) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREWERY, brewery);
        BreweryDetailFragment fragment = new BreweryDetailFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_brewery_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);
        brewery = getArguments().getParcelable(ARG_BREWERY);
        Picasso.with(getContext()).load(brewery.getImage()).fit().into(breweryImage);
        breweryName.setText(brewery.getName());

        beerList = new ArrayList<>();
        getBeersByBreweryId(brewery.getId());

        //Sets the beer recycler view with the list of beers from network call.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BeerRecyclerViewAdapter(beerList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Network call to retrieve a list of beers from the brewery API.
     *
     * @param breweryId - id of brewery whose row was selected.
     */
    public void getBeersByBreweryId(String breweryId) {
        Log.d(TAG, "brewery: " + breweryId);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Call<BeerWrapper> beersCall = ApiManager.getBreweryDBService().getBeersByBrewery(breweryId);
            beersCall.enqueue(new Callback<BeerWrapper>() {
                @Override
                public void onResponse(Call<BeerWrapper> call, Response<BeerWrapper> response) {
                        BeerWrapper beerWrapper = response.body();
                        if (beerWrapper.getData() != null) {
                            beerList.addAll(beerWrapper.getData());
                            adapter.notifyDataSetChanged();
                        }
                }

                @Override
                public void onFailure(Call<BeerWrapper> call, Throwable t) {
                    Log.d(TAG, "Failed to get beers from database");
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

}
