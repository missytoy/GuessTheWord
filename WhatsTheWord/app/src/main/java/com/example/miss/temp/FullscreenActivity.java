package com.example.miss.temp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import data.DataAccess;
import layout.AddNewWord;
import layout.CategoriesFragment;
import layout.GamePage;
import layout.HistoryFragment;
import layout.MenuPageFragmetn;
import layout.Ranking;
import layout.StartNewGameFragment;
import models.Category;
import models.Player;

public class FullscreenActivity extends AppCompatActivity
        implements
        MenuPageFragmetn.OnButtonsClick,
        StartNewGameFragment.OnChooseCategoryBtnClicked,
        StartNewGameFragment.IOnGeolocationChosen,
        CategoriesFragment.OnListViewItemSelected,
        HistoryFragment.IGoToMainPagePressedFromHistory,
        Ranking.IGoToMainPagePressed,
        GamePage.OnGameOver,
        LocationListener {

    private LocationManager locationManager;
    private String provider;
    private  Boolean isChecked;

    private DataAccess data;
    private List<Player> playersList;
   private List<Category> categoriesList;
   private Address currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        data = new DataAccess(this);
        new GetCategoriesTask().execute(data);

        playersList = new ArrayList<Player>();

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (findViewById(R.id.fragment_placeholder) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            MenuPageFragmetn firstFragment = new MenuPageFragmetn();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_placeholder, firstFragment);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        data.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.open();
    }

    @Override
    public void onBackPressed() {
        MenuPageFragmetn firstFragment = new MenuPageFragmetn();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_placeholder, firstFragment);
        transaction.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        data.open();
    }

    @Override
    public Address getLocation(){
        return this.currentLocation;
    }

    @Override
    public void getGeolocation() throws IOException {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        if (!((LocationManager) this.getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(callGPSSettingIntent, 1);
                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }else{
            //gps is enabled
        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 1 * 60 * 1000) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            currentLocation = addresses.get(0);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            Log.v("Location address", address);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }

        int b = 5;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private class GetCategoriesTask extends AsyncTask<DataAccess, Void, List<Category>> {
        @Override
        protected List<Category> doInBackground(DataAccess... params) {
            data.open();
            List<Category> categories = params[0].getAllCategories();

            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            categoriesList = new ArrayList<Category>(categories);
        }
    }

    @Override
    public void onGameEnding(String[] playersScores) {
        Ranking newFragment = new Ranking();
        Bundle args = new Bundle();
        args.putSerializable("players_scores", playersScores);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onCategoryItemClicked(int categoryId) {
        GamePage newFragment = new GamePage();
        Bundle args = new Bundle();
        args.putInt("category_id", categoryId);
        args.putBoolean("is_checked",this.isChecked);
        args.putSerializable("players_list", (Serializable) playersList);
        if (currentLocation != null) {
            args.putString("location", currentLocation.getAddressLine(0));
            args.putString("city", currentLocation.getLocality());
        }

        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onStartButtonClicked() {
        StartNewGameFragment newFragment = new StartNewGameFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void goToMainPageFromRanking() {


        MenuPageFragmetn firstFragment = new MenuPageFragmetn();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_placeholder, firstFragment);
        transaction.commit();
    }

    @Override
    public void goToMainPageFromHistory() {

        MenuPageFragmetn firstFragment = new MenuPageFragmetn();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_placeholder, firstFragment);
        transaction.commit();
    }

    @Override
    public void onHistoryButtonClicked() {

        HistoryFragment newFragment = new HistoryFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onAddWordButtonClicked() {
        AddNewWord newFragment = new AddNewWord();
        Bundle args = new Bundle();
        args.putSerializable("categories_array", (Serializable) categoriesList);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onChooseCategoryButtonClicked(Serializable players, Boolean isChecked) {
        playersList = new ArrayList<Player>((ArrayList<Player>) players);
        this.isChecked = isChecked;

        Bundle args = new Bundle();
        args.putSerializable("categories_array", (Serializable) categoriesList);

        // Go to choose category fragment;

        CategoriesFragment newFragment = new CategoriesFragment();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }
}
