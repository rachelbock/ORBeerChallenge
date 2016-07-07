package com.rage.oregonbeerchallenge;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main Activity launched at app startup. Sets up toolbar and adds the home fragment to the layout.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_activity_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //add action bar
        toolbar.setTitle(R.string.main_title_text);
        setSupportActionBar(toolbar);

        //home fragment displayed on main activity
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_frame_layout, HomeFragment.newInstance());
        transaction.commit();
    }

    /**
     * Inflates the toolbar menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Handles the toolbar menu item selection. Replaces whatever fragment is currently displayed
     * with the HomeFragment.
     * @param item - there is only one item - the home button
     * @return - returns true once completed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        clearBackStack();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity_frame_layout, HomeFragment.newInstance());
        transaction.commit();
        return true;
    }


    /**
     * Method to clear all items from the backstack. Called whenever the home button is selected in
     * order to prevent buildup of fragments.
     */
    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
