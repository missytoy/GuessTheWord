package com.example.miss.temp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.View;
import android.widget.Button;

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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity
        implements View.OnClickListener,
        MenuPageFragmetn.OnButtonsClick,
        StartNewGameFragment.OnChooseCategoryBtnClicked,
        StartNewGameFragment.IOnGeolocationChosen,
        CategoriesFragment.OnListViewItemSelected,
        HistoryFragment.IGoToMainPagePressedFromHistory,
        Ranking.IGoToMainPagePressed,
        GamePage.OnGameOver,
        LocationListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    private LocationManager locationManager;
    private String provider;
    private  Boolean isChecked;

    public DataAccess data;
    Button closeAppBtn;
    private List<Player> playersList;
    List<Category> categoriesList;
    Address currentLocation;
    Location currentLoc;

    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

//    private View mContentView;
//    private View mControlsView;
//    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        data = new DataAccess(this);
        new GetCategoriesTask().execute(data);

        playersList = new ArrayList<Player>();
//        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
//        mContentView = findViewById(R.id.fullscreen_content);

//        closeAppBtn = (Button) findViewById(R.id.dummy_button);
//        closeAppBtn.setOnClickListener(this);

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

        // Set up the user interaction to manually show or hide the system UI.

//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        data.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.open();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
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
    public void getGeolocation() throws IOException {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null /* location.getTime() > Calendar.getInstance().getTimeInMillis() - 1 * 60 * 1000*/) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            currentLocation = addresses.get(0);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
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
            currentLoc = location;
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            //locationManager.removeUpdates(this);
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

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
//    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (AUTO_HIDE) {
//                delayedHide(AUTO_HIDE_DELAY_MILLIS);
//            }
//            return false;
//        }
//    };

//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

//    private void hide() {
//        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        mControlsView.setVisibility(View.GONE);
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
//    }

//    private final Runnable mHidePart2Runnable = new Runnable() {
//        @SuppressLint("InlinedApi")
//        @Override
//        public void run() {
//            // Delayed removal of status and navigation bar
//
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
//    };
    @SuppressLint("InlinedApi")
//    private void show() {
//        // Show the system bar
//        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        mVisible = true;
//
//        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//    }

//    private final Runnable mShowPart2Runnable = new Runnable() {
//        @Override
//        public void run() {
//            // Delayed display of UI elements
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
//            mControlsView.setVisibility(View.VISIBLE);
//        }
//    };

//    private final Handler mHideHandler = new Handler();
//    private final Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            hide();
//        }
//    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }


    @Override
    public void onClick(View v) {
        ////TODO: Here clear the back stack of fragments!!
        // if (v.getId() == closeAppBtn.getId()) {
//          //  Toast.makeText(getApplicationContext(), "Closed", Toast.LENGTH_SHORT).show();
//             MenuPageFragmetn firstFragment = new MenuPageFragmetn();
//             getSupportFragmentManager().beginTransaction()
//                     .add(R.id.fragment_placeholder, firstFragment).commit();
        //  }
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
        // Maybe put here the game object with location string to be persisted to base
        args.putSerializable("data", (Serializable) data);
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
        Bundle args = new Bundle();
        args.putSerializable("data", this.data);
        newFragment.setArguments(args);

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
        //GamePage newFragment = new GamePage();

        Bundle args = new Bundle();
        args.putSerializable("data", this.data);
        newFragment.setArguments(args);

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
        args.putSerializable("data", (Serializable) data);
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
