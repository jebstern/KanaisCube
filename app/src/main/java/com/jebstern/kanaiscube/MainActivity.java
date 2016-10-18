package com.jebstern.kanaiscube;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences prefs;
    ProgressDialog mProgressDialog;
    private DrawerLayout mDrawerLayout;
    private final static String STATE_SELECTED_POSITION = "position";
    private int mCurrentSelectedPosition;
    private NavigationView mNavigationView;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(this);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(this);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);


        prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean dbsetup = prefs.getBoolean("dbsetup", false);

        if (!dbsetup) {
            new DbSetupTasker().execute();
        }

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SeasonFragment()).commit();
        }

    }


    private class DbSetupTasker extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("First time setup");
            mProgressDialog.setMessage("Sorry, this will only take a few seconds");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper helper = new DBHelper(getApplicationContext());
            helper.initializeArmors();
            helper.initializeWeapons();
            helper.initializeJewelry();
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("dbsetup", true);
            editor.apply();
            mProgressDialog.dismiss();
        }

    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }


    @Override
    public void onClick(View v) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.item_season:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SeasonFragment()).commit();
                mDrawerLayout.closeDrawers();
                return true;
            case R.id.item_normal:
                mCurrentSelectedPosition = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new NormalFragment()).commit();
                mDrawerLayout.closeDrawers();
                return true;
            case R.id.item_progress_season:
                mCurrentSelectedPosition = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProgressSeasonFragment()).commit();
                mDrawerLayout.closeDrawers();
                return true;
            case R.id.item_progress_normal:
                mCurrentSelectedPosition = 3;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProgressNormalFragment()).commit();
                mDrawerLayout.closeDrawers();
                return true;
            case R.id.item_settings:
                mCurrentSelectedPosition = 4;
                getFragmentManager().beginTransaction().replace(R.id.container, new PrefsFragment()).commit();
                mDrawerLayout.closeDrawers();
                return true;
        }

        return false;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}