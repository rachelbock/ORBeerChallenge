package com.rage.oregonbeerchallenge.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rage.oregonbeerchallenge.Data.Beer;
import com.rage.oregonbeerchallenge.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display the list of beers by brewery.
 */
public class BeerRecyclerViewAdapter extends RecyclerView.Adapter<BeerRecyclerViewAdapter.BeerViewHolder>{


    public static final String TAG = BeerRecyclerViewAdapter.class.getSimpleName();
    protected List<Beer> beers;

    public BeerRecyclerViewAdapter(List<Beer> beers) {
        this.beers = beers;
    }


    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View beersView = inflater.inflate(R.layout.beer_row, parent, false);
        return new BeerViewHolder(beersView);
    }

    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = beers.get(position);
        holder.beerName.setText(beer.getName());
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    /**
     * View Holder to set up beer row resources.
     */
    public static class BeerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.beer_row_name_text_view)
        protected TextView beerName;

        public BeerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
