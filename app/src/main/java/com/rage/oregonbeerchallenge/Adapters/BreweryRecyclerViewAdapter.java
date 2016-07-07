package com.rage.oregonbeerchallenge.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rage.oregonbeerchallenge.BreweryDetailFragment;
import com.rage.oregonbeerchallenge.Data.BreweryObj;
import com.rage.oregonbeerchallenge.R;
import com.rage.oregonbeerchallenge.VisitedBrewerySQLiteHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to display each brewery row.
 */
public class BreweryRecyclerViewAdapter extends RecyclerView.Adapter<BreweryRecyclerViewAdapter.BreweryViewHolder> {

    private List<BreweryObj> breweries;
    private Context context;
    private VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper;
    private FragmentActivity fragmentActivity;

    public static final String TAG = BreweryRecyclerViewAdapter.class.getSimpleName();

    public BreweryRecyclerViewAdapter(List<BreweryObj> breweries, VisitedBrewerySQLiteHelper visitedBrewerySQLiteHelper, FragmentActivity fragmentActivity, Context context) {
        this.context = context;
        this.visitedBrewerySQLiteHelper = visitedBrewerySQLiteHelper;
        this.fragmentActivity = fragmentActivity;
        this.breweries = breweries;
    }

    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View breweriesView = inflater.inflate(R.layout.breweries_row, parent, false);

        return new BreweryViewHolder(breweriesView);
    }

    @Override
    public void onBindViewHolder(final BreweryViewHolder holder, int position) {
        final BreweryObj breweryObj = breweries.get(position);

        Picasso.with(context).load(breweryObj.getImage()).fit().centerCrop().into(holder.breweryImage);

        holder.breweryName.setText(breweryObj.getName());

        holder.visitedCheckBox.setChecked(breweryObj.getVisited());

        //On Click Listener for visited checkbox to add and remove visited brewery locations from
        //local SQLite database.
        holder.visitedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreweryObj oneBrewery = breweries.get(holder.getAdapterPosition());
                if (holder.visitedCheckBox.isChecked()) {
                    holder.visitedCheckBox.setChecked(true);
                    oneBrewery.setVisited(true);
                    visitedBrewerySQLiteHelper.addVisitedBrewery(oneBrewery);
                }
                else {
                 visitedBrewerySQLiteHelper.removeVisitedBrewery(oneBrewery.getId());
                    holder.visitedCheckBox.setChecked(false);
                }

            }
        });

        //On Click listener for the entire brewery row. Launches the detail fragment
        holder.breweryRowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreweryObj oneBrewery = breweries.get(holder.getAdapterPosition());
                FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_frame_layout, BreweryDetailFragment.newInstance(oneBrewery));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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
