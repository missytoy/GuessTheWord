package layout;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import data.DataAccess;
import helpers.MySoundManager;
import models.Game;
import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage extends Fragment implements View.OnClickListener, SensorEventListener {
    private static final Random random = new Random();

    SensorEventListener listener;

    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    private static final int PLAYER_TURN_TIME = 10000;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private List<String> wordsToGuess;
    private List<Player> players;
    private Integer currentCategoryId;
    private Integer currentPlayerIndex;
    private HashSet<String> usedWords;
    private String address;
    private String city;
    private String place;
    private Boolean isLocationChecked;

    TextView timerTextView;
    TextView randomWord;

    RelativeLayout randomWordAndTimer;
    RelativeLayout currentUserInfo;
    RelativeLayout playerFirstPage;

    Button nextPlayerButton;
    TextView playerScoreView;

    public Long timerStep;
    private OnGameOver onGameOver;

    Button correctButton;
    Button wrongButton;

    Button startWithFirstPlayerButton;

    public GamePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_game_page, container, false);

        wordsToGuess = new ArrayList<String>();
        usedWords = new HashSet<String>();

        Bundle args = this.getArguments();
        isLocationChecked = args.getBoolean("is_checked");
        currentCategoryId = args.getInt("category_id");
        address = args.getString("location");
        city = args.getString("city");
        players = new ArrayList<Player>((List<Player>) args.getSerializable("players_list"));
        currentPlayerIndex = 0;

        new GetWordsTask().execute((DataAccess) args.getSerializable("data"));

        startWithFirstPlayerButton = (Button) view.findViewById(R.id.first_player_play_btn);
        String firstPlayerName = players.get(currentPlayerIndex).getName();
        startWithFirstPlayerButton.setText(firstPlayerName + "'s turn");
        startWithFirstPlayerButton.setOnClickListener(this);


        timerTextView = (TextView) view.findViewById(R.id.timer_textview_id);
        randomWord = (TextView) view.findViewById(R.id.random_word_textview_id);

        randomWordAndTimer = (RelativeLayout) view.findViewById(R.id.random_word_and_timer_Layout);
        currentUserInfo = (RelativeLayout) view.findViewById(R.id.current_user_info);
        playerFirstPage = (RelativeLayout) view.findViewById(R.id.fist_player_layout);

        nextPlayerButton = (Button) view.findViewById(R.id.next_player_button);
        nextPlayerButton.setOnClickListener(this);

        playerScoreView = (TextView) view.findViewById(R.id.player_score_view);

        correctButton = (Button) view.findViewById(R.id.correct_btn);
        correctButton.setOnClickListener(this);

        wrongButton = (Button) view.findViewById(R.id.wrong_btn);
        wrongButton.setOnClickListener(this);

        startWithFirstPlayerButton = (Button) view.findViewById(R.id.first_player_play_btn);
        startWithFirstPlayerButton.setOnClickListener(this);

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {


                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                if (playerFirstPage.getVisibility() != View.VISIBLE) {

                                    handleSwipeLeft();
                                }
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                if (playerFirstPage.getVisibility() != View.VISIBLE) {

                                    handleSwipeRight();
                                }
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
//
//        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        listener = this;
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == nextPlayerButton.getId()) {

            MySoundManager.playButtonSound(getContext());
            randomWordAndTimer.setVisibility(View.VISIBLE);
            currentUserInfo.setVisibility(View.INVISIBLE);
            usedWords.clear();
            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            randomWord.setText(wordsToGuess.get(indexOfRandomWord));
            playerTimer();
        } else if (v.getId() == correctButton.getId()) {

            MySoundManager.playCorrectTone(getContext());
            Player currentPlayer = players.get(currentPlayerIndex);
            int currentScore = currentPlayer.getScore();
            currentPlayer.setScore(currentScore + 1);

            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            String wordToGuess = wordsToGuess.get(indexOfRandomWord);
            while (usedWords.contains(wordToGuess)) {
                indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
                wordToGuess = wordsToGuess.get(indexOfRandomWord);
            }

            randomWord.setText(wordToGuess);
            usedWords.add(wordToGuess);

        } else if (v.getId() == wrongButton.getId()) {

            MySoundManager.playNextWordTone(getContext());
            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            String wordToGuess = wordsToGuess.get(indexOfRandomWord);
            while (usedWords.contains(wordToGuess)) {
                indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
                wordToGuess = wordsToGuess.get(indexOfRandomWord);
            }

            randomWord.setText(wordToGuess);
            usedWords.add(wordToGuess);
        } else if (v.getId() == startWithFirstPlayerButton.getId()) {


            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            String wordToGuess = wordsToGuess.get(indexOfRandomWord);
            randomWord.setText(wordToGuess);
            usedWords.add(wordToGuess);
            playerFirstPage.setVisibility(View.GONE);
            randomWordAndTimer.setVisibility(View.VISIBLE);
            playerTimer();
        }

    }

    public void playerTimer() {
        CountDownTimer waitTimer;
        //TODO: set timer to 60000
        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        listener = this;
        waitTimer = new CountDownTimer(PLAYER_TURN_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                timerStep = millisUntilFinished / 1000;
                timerTextView.setText(timerStep.toString());
            }

            public void onFinish() {
                timerTextView.setText("0");
                senSensorManager.unregisterListener(listener);
                if (currentPlayerIndex == players.size() - 1) {
                    Collections.sort(players);
                    String[] playerScores = new String[players.size()];
                    for (int i = 0; i < players.size(); i++) {
                        String playerScoreInfo = String.format("%d. %s (%d points)",
                                i + 1,
                                players.get(i).getName(),
                                players.get(i).getScore());
                        playerScores[i] = playerScoreInfo;
                    }

                    // Make async task that saves the game object to the database - here or in activity see how to do it.
                    new SaveGameObjectAndPlayersToBaseTask().execute((DataAccess) getArguments().getSerializable("data"));
                    onGameOver.onGameEnding(playerScores);
                } else {

                    randomWordAndTimer.setVisibility(View.INVISIBLE);
                    currentUserInfo.setVisibility(View.VISIBLE);


                    String playerScoreText = String.format("%s has %d points.",
                            players.get(currentPlayerIndex).getName(),
                            players.get(currentPlayerIndex).getScore());

                    String nextButtonPlayerText = String.format("%s's turn",
                            players.get(currentPlayerIndex + 1).getName());

                    playerScoreView.setText(playerScoreText);
                    nextPlayerButton.setText(nextButtonPlayerText);
                    currentPlayerIndex++;
                }
            }
        }.start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

//        if (randomWordAndTimer.getVisibility() == View.VISIBLE) {
//            senSensorManager.unregisterListener(this);


        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 1000) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 50000;

                if (speed > SHAKE_THRESHOLD) {

                }

                if (z > 3 && y < 5 && x < 0) {            //x>0
                    // Log.d("pingosvam","z > 3 && y<3 gore");
                    //  MySoundManager.playCorrectTone(getContext());
                    if (playerFirstPage.getVisibility() != View.VISIBLE) {

                        handleSwipeLeft();
                    }

                }
                Log.d("pingosvam x", String.valueOf(x));

//                if (z < -5 && y>3 && x>1){
//                    Log.d("ping","normalno");
//
//                }


                if (z < -3 && y < 3 && x < 1) {
                    //   Log.d("ping", "dolu");
                    //   MySoundManager.playNextWordTone(getContext());
                    if (playerFirstPage.getVisibility() != View.VISIBLE) {

                        handleSwipeRight();
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnGameOver {
        void onGameEnding(String[] playersScores);
    }

    public void handleSwipeLeft() {

        MySoundManager.playCorrectTone(getContext());
        Player currentPlayer = players.get(currentPlayerIndex);
        int currentScore = currentPlayer.getScore();
        currentPlayer.setScore(currentScore + 1);

        int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
        String wordToGuess = wordsToGuess.get(indexOfRandomWord);
        while (usedWords.contains(wordToGuess)) {
            indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            wordToGuess = wordsToGuess.get(indexOfRandomWord);
        }

        randomWord.setText(wordToGuess);
        usedWords.add(wordToGuess);
    }

    public void handleSwipeRight() {

        MySoundManager.playNextWordTone(getContext());
        int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
        String wordToGuess = wordsToGuess.get(indexOfRandomWord);
        while (usedWords.contains(wordToGuess)) {
            indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            wordToGuess = wordsToGuess.get(indexOfRandomWord);
        }

        randomWord.setText(wordToGuess);
        usedWords.add(wordToGuess);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onGameOver = (OnGameOver) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGameOver");
        }
    }

    private class GetWordsTask extends AsyncTask<DataAccess, Void, AbstractSet<String>> {
        @Override
        protected AbstractSet<String> doInBackground(DataAccess... params) {
            AbstractSet<String> words = params[0].getAllWordsContentByCategory(currentCategoryId);

            return words;
        }

        @Override
        protected void onPostExecute(AbstractSet<String> words) {
            if (words.size() > 0) {
                for (String word : words) {
                    wordsToGuess.add(word);
                }
            } else {
                randomWord.setText("No words in category");
                Toast toast = Toast.makeText(getContext(), "Go back and ad some words to this category", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    private class SaveGameObjectAndPlayersToBaseTask extends AsyncTask<DataAccess, Void, Void> {
        @Override
        protected Void doInBackground(DataAccess... params) {
            Game gameModel = new Game();
            gameModel.setCategoryId(currentCategoryId);
            gameModel.setPlayedOn(new Date());


            if (isLocationChecked) {
                place = city + ", " + address;
                gameModel.setLocation(place);
            }


            int gameId = (int) params[0].createGame(gameModel);
            for (Player pl : players) {
                pl.setGameId(gameId);
                params[0].createPlayer(pl);
            }
            Collections.sort(players);
            return null;
        }
    }

}
