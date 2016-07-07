package com.rage.oregonbeerchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rage.oregonbeerchallenge.Adapters.BreweryRecyclerViewAdapter;
import com.rage.oregonbeerchallenge.Data.BreweryObj;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Fragment that is displayed at application launch.
 */
public class HomeFragment extends Fragment {

    @Bind(R.id.home_fragment_recycler_view)
    protected RecyclerView recyclerView;
    @Bind(R.id.home_fragment_progress_text_view)
    protected TextView progressText;

    public static final String TAG = HomeFragment.class.getSimpleName();


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Returns an instance of HomeFragment.
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);


        List<BreweryObj> breweries = new ArrayList<>();
        VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper = VisitedBrewerySQLiteHelper.getInstance(getContext());
        breweries = visitedBrewerySQLiteHelper.getVisitedBreweries();

        if (breweries.size() > 0) {
            progressText.setText(getContext().getString(R.string.you_ve_visited_s_of_oregon_s_breweries, breweries.size()));
        }

        //Sets the brewery recycler view up giving it the list of breweries from the database.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BreweryRecyclerViewAdapter adapter = new BreweryRecyclerViewAdapter(breweries, visitedBrewerySQLiteHelper, getActivity(), getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * Launches the breweries fragment when the add brewery button is selected.
     */
    @OnClick(R.id.home_fragment_view_breweries_fab)
    public void onViewBreweriesButtonClicked() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_frame_layout, BreweriesFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
