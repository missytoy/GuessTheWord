package com.example.miss.temp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data.DataAccess;
import layout.AddNewWord;
import layout.CategoriesFragment;
import layout.GamePage;
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
        CategoriesFragment.OnListViewItemSelected,
        GamePage.OnGameOver{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    public DataAccess data;
    Button closeAppBtn;
    private List<Player> playersList;
    List<Category> categoriesList;

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
        args.putSerializable("players_list", (Serializable) playersList);
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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_placeholder, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onHistoryButtonClicked() {

        Ranking newFragment = new Ranking();
    //    HistoryFragment newFragment = new HistoryFragment();
        //GamePage newFragment = new GamePage();

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
    public void onChooseCategoryButtonClicked(Serializable players) {
        playersList = new ArrayList<Player>((ArrayList<Player>) players);

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
