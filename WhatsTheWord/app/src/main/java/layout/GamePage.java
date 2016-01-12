package layout;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import models.Game;
import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage extends Fragment implements View.OnClickListener {
    private static final Random random = new Random();

    private List<String> wordsToGuess;
    private List<Player> players;
    private Integer currentCategoryId;
    private Integer currentPlayerIndex;
    private HashSet<String> usedWords;
    int randNum;

    TextView timerTextView;
    TextView randomWord;
    RelativeLayout randomWordAndTimer;
    RelativeLayout currentUserInfo;
    Button nextPlayerButton;
    TextView playerScoreView;

    public Long timerStep;
    private OnGameOver onGameOver;

    Button correctButton;
    Button wrongButton;

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
        currentCategoryId = args.getInt("category_id");
        players = new ArrayList<Player>((List<Player>) args.getSerializable("players_list"));
        currentPlayerIndex = 0;

        new GetWordsTask().execute((DataAccess) args.getSerializable("data"));

        timerTextView = (TextView) view.findViewById(R.id.timer_textview_id);
        randomWord = (TextView) view.findViewById(R.id.random_word_textview_id);
        randomWordAndTimer = (RelativeLayout) view.findViewById(R.id.random_word_and_timer_Layout);
        currentUserInfo = (RelativeLayout) view.findViewById(R.id.current_user_info);
        nextPlayerButton = (Button) view.findViewById(R.id.next_player_button);
        nextPlayerButton.setOnClickListener(this);

        playerScoreView = (TextView) view.findViewById(R.id.player_score_view);

        correctButton = (Button) view.findViewById(R.id.correct_btn);
        wrongButton = (Button) view.findViewById(R.id.wrong_btn);
        correctButton.setOnClickListener(this);
        wrongButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextPlayerButton.getId()) {

            playNextWordTone(getContext());
            randomWordAndTimer.setVisibility(View.VISIBLE);
            currentUserInfo.setVisibility(View.INVISIBLE);
            usedWords.clear();
            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            randomWord.setText(wordsToGuess.get(indexOfRandomWord));
            playerTimer();
        } else if (v.getId() == correctButton.getId()) {

            playCorrectTone(getContext());
            Player currentPlayer = players.get(currentPlayerIndex);
            int currentScore = currentPlayer.getScore();
            currentPlayer.setScore(currentScore + 1);
            // TODO: notify with sound

            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            String wordToGuess = wordsToGuess.get(indexOfRandomWord);
            while (usedWords.contains(wordToGuess)) {
                indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
                wordToGuess = wordsToGuess.get(indexOfRandomWord);
            }

            randomWord.setText(wordToGuess);
            usedWords.add(wordToGuess);

        } else if (v.getId() == wrongButton.getId()) {
            // TODO: notify with sound
            playNextWordTone(getContext());
            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            String wordToGuess = wordsToGuess.get(indexOfRandomWord);
            while (usedWords.contains(wordToGuess)) {
                indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
                wordToGuess = wordsToGuess.get(indexOfRandomWord);
            }

            randomWord.setText(wordToGuess);
            usedWords.add(wordToGuess);
        }
    }

    public void playerTimer() {
        CountDownTimer waitTimer;
        //TODO: set timer to 60000
        waitTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerStep = millisUntilFinished / 1000;
                timerTextView.setText(timerStep.toString());
            }

            public void onFinish() {
                timerTextView.setText("0");
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

    public interface OnGameOver {
        void onGameEnding(String[] playersScores);
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

                int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
                String wordToGuess = wordsToGuess.get(indexOfRandomWord);
                randomWord.setText(wordToGuess);
                usedWords.add(wordToGuess);

                playerTimer();
            } else {
                randomWord.setText("No words in category");
                // TODO: stylize this toast;
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
            // TODO: see if this one works correctly
            gameModel.setPlayedOn(new Date());
            // TODO: get the location from the activity as a string via Bundle
            gameModel.setLocation("some location string");

            int gameId = (int) params[0].createGame(gameModel);
            for (Player pl : players) {
                pl.setGameId(gameId);
                params[0].createPlayer(pl);
            }

            return null;
        }
    }

    public void playCorrectTone(final Context context) {


        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                player = MediaPlayer.create(context, R.raw.correct);
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        t.start();

    }

    public void playNextWordTone(final Context context) {

        Random rand = new Random();
        randNum = rand.nextInt(2);

        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                if (randNum == 1) {

                    player = MediaPlayer.create(context, R.raw.hahasound);
                } else {
                    player = MediaPlayer.create(context, R.raw.flip);
                }
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        t.start();

    }

}
