package com.rage.oregonbeerchallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.oregonbeerchallenge.Data.BreweryData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display each brewery row.
 */
public class BreweryRecyclerViewAdapter extends RecyclerView.Adapter<BreweryRecyclerViewAdapter.BreweryViewHolder> {

    private List<BreweryData> breweries;
    private Context context;
    private VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper;

    public BreweryRecyclerViewAdapter(List<BreweryData> breweries, Context context) {
        this.context = context;
        this.breweries = breweries;
    }

    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View breweriesView = inflater.inflate(R.layout.breweries_row, parent, false);

        VisitedBrewerySQLiteHelper.getInstance(context);
        return new BreweryViewHolder(breweriesView);
    }

    @Override
    public void onBindViewHolder(final BreweryViewHolder holder, int position) {
        BreweryData breweryData = breweries.get(position);

        if (breweryData.getImages() != null) {
            Picasso.with(context).load(breweryData.getImages().getIcon()).fit().centerCrop().into(holder.breweryImage);
        }
        holder.breweryName.setText(breweryData.getName());
//
//        //On Click Listener for visited checkbox to add and remove visited brewery locations from
//        //local SQLite database.
//        holder.visitedCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.visitedCheckBox.isChecked()) {
//                    //add to database
//                    //TODO: Update recyclerview to accept breweryobj instead of brewery data
//                    visitedBrewerySQLiteHelper.addVisitedBrewery(breweries.get(position));
//                }
//                else {
//                 //remove from database
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return breweries.size();
    }

    public static class BreweryViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.brewery_row_image)
        protected ImageView breweryImage;
        @Bind(R.id.brewery_row_name_text)
        protected TextView breweryName;
        @Bind(R.id.brewery_row_visited_checkbox)
        protected CheckBox visitedCheckBox;
        protected View breweryRowView;

        public BreweryViewHolder(View itemView) {
            super(itemView);
            breweryRowView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
