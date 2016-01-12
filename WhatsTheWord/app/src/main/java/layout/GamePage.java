package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.DataAccess;
import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage extends Fragment implements View.OnClickListener {

    private static final Random random = new Random();

    private List<String> wordsToGuess;
    private List<Player> players;
    private Integer currentCategoryId;

    TextView timerTextView;
    TextView randomWord;
    RelativeLayout randomWordAndTimer;
    RelativeLayout currentUserInfo;
    Button nextPlayerButton;
    public Long timerStep;

    public GamePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_game_page, container, false);

        wordsToGuess = new ArrayList<String>();
        Bundle args = this.getArguments();
        currentCategoryId = args.getInt("category_id");
        players = new ArrayList<Player>((List<Player>) args.getSerializable("players_list"));

        new GetWordsTask().execute((DataAccess) args.getSerializable("data"));

        timerTextView = (TextView) view.findViewById(R.id.timer_textview_id);
        randomWord = (TextView) view.findViewById(R.id.random_word_textview_id);
        randomWordAndTimer = (RelativeLayout) view.findViewById(R.id.random_word_and_timer_Layout);
        currentUserInfo = (RelativeLayout) view.findViewById(R.id.current_user_info);
        nextPlayerButton = (Button) view.findViewById(R.id.next_player_button);
        nextPlayerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextPlayerButton.getId()) {

            randomWordAndTimer.setVisibility(View.VISIBLE);
            currentUserInfo.setVisibility(View.INVISIBLE);
            int indexOfRandomWord = random.nextInt(wordsToGuess.size() - 1 + 1);
            randomWord.setText(wordsToGuess.get(indexOfRandomWord));
            playerTimer();
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

                randomWordAndTimer.setVisibility(View.INVISIBLE);
                currentUserInfo.setVisibility(View.VISIBLE);
            }
        }.start();
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
                randomWord.setText(wordsToGuess.get(indexOfRandomWord));

                playerTimer();
            }
            else{
                randomWord.setText("No words in category");
                // TODO: stylize this toast;
                Toast.makeText(getContext(), "Go back and ad some words to this category", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
